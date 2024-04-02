package com.gradu.infou.Service;

import com.gradu.infou.Auth.Utils.JwtUtil;
import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.AddInfouReqDto;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Dto.Controller.Kind;
import com.gradu.infou.Domain.Dto.Controller.PortalSearchAggregationResult;
import com.gradu.infou.Domain.Dto.Service.InfouDetailResDto;
import com.gradu.infou.Domain.Dto.Service.SearchLectureResDto;
import com.gradu.infou.Domain.Entity.InfouDocument;
import com.gradu.infou.Domain.Entity.InfouProcessDocument;
import com.gradu.infou.Domain.Entity.User;
import com.gradu.infou.Repository.InfouProcessRepository;
import com.gradu.infou.Repository.InfouRepository;
import com.gradu.infou.Repository.UserRepository;
import com.gradu.infou.converter.InfouConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.ValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class InfouService {
    private final InfouRepository infouRepository;
    private final InfouProcessRepository infouProcessRepository;
    private final RestHighLevelClient elasticsearchClient;
    private final UserRepository userRepository;
    private final LogService logService;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private String INDEX="lecture_infou";
    @Value("${jwt.secret.access}")
    private String secretAccessKey;
    private final ElasticQueryService elasticQueryService;


    public InfouDocument addInfou(HttpServletRequest request, AddInfouReqDto addInfouReqDto){
        String token = jwtUtil.resolveToken(request);
        String id = jwtUtil.getId(token, secretAccessKey);
        Long idL = Long.parseLong(id);
        User user = userRepository.findById(idL).orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_NOT_FOUND));

        //강의평 작성 xss 보안 추가 필요
        InfouDocument infouDocument = InfouConverter.toInfouDocument(id, addInfouReqDto);
        infouRepository.save(infouDocument);

        return infouDocument;
    }

    @Transactional
    public void addInfouProcess(InfouDocument infouDocument){
        InfouProcessDocument infouProcessDocument = infouProcessRepository.findByAcademicNumberAndProfessorName(infouDocument.getAcademicNumber(), infouDocument.getProfessorName());
        if(infouProcessDocument==null){
            InfouProcessDocument newInfouProcessDocument = InfouProcessDocument.toInfouProcessDocument(infouDocument, infouDocument.getScore(), infouDocument.getScore(),1);
            infouProcessRepository.save(newInfouProcessDocument);
        }
        else{
            infouProcessDocument.setCount(infouProcessDocument.getCount()+1);
            infouProcessDocument.setSumValue(infouProcessDocument.getSumValue()+infouDocument.getScore());
            infouProcessDocument.setAverageValue(infouProcessDocument.getSumValue()/ infouProcessDocument.getCount());
            infouProcessRepository.save(infouProcessDocument);
        }
    }

    public Page<InfouProcessDocument> searchInfou(String keyword, Kind condition, Pageable pageable) throws IOException {

        switch (condition){
            case department:
                return infouProcessRepository.findAllByDepartment(keyword, pageable);
            case lecture_name:
                return infouProcessRepository.findAllByLectureName(keyword, pageable);
            default:
                return infouProcessRepository.findAllByProfessorName(keyword, pageable);
        }
    }

    public InfouDetailResDto detailInfou(HttpServletRequest request, String academicNumber, String professorName, Pageable pageable) throws IOException {
        //infou 합계 데이터
        SearchResponse searchResponse = searchLecturesWithAggregations(academicNumber, professorName);

        Long total = searchResponse.getHits().getTotalHits().value;
        HashMap<String, Double> levelRatio = getRatio(searchResponse, "level_ratio");
        HashMap<String, Double> skillRatio = getRatio(searchResponse, "skill_ratio");
        HashMap<String, Double> gradeRatio = getRatio(searchResponse, "grade_ratio");

        //infou 강의평
        Page<InfouDocument> infouDocuments = infouRepository.findByAcademicNumberAndProfessorName(academicNumber, professorName, pageable);

        InfouProcessDocument infouProcessDocument = infouProcessRepository.findByAcademicNumberAndProfessorName(academicNumber, professorName);
        logService.addLog(request,"infou",infouProcessDocument.getId());

        return InfouDetailResDto.fromEntity(total, levelRatio, skillRatio, gradeRatio, infouDocuments);
    }

    public Page<InfouDocument> popularGE(Pageable pageable){
        List<String> GE = new ArrayList<>();
        GE.add("일선");
        GE.add("교선");
        GE.add("교필");
        Page<InfouDocument> infouDocuments = infouRepository.findByLectureTypeIn(GE, pageable);

        return infouDocuments;
    }

    public Page<InfouDocument> recentInfou(Pageable pageable){
        Page<InfouDocument> all = infouRepository.findAll(pageable);
        return all;
    }

    public Page<InfouProcessDocument> recommendInfouList(HttpServletRequest request, Pageable pageable) throws IOException {
        User user = userService.findUserByRequest(request);
        Page<InfouProcessDocument> infouProcessDocuments = elasticQueryService.searchRecommend(user.getGrade(), user.getMajor(), "infou", pageable);
        return infouProcessDocuments;
    }


    private HashMap<String, Double> getRatio(SearchResponse searchResponse, String attribute){

        Terms terms = searchResponse.getAggregations().get(attribute);
        Long total = searchResponse.getHits().getTotalHits().value;

        HashMap<String, Double> result = new LinkedHashMap<>();
        HashMap<String, Double> result2 = new LinkedHashMap<>();

        for(Terms.Bucket bucket : terms.getBuckets()){
            String key = bucket.getKey().toString();
            Long value = bucket.getDocCount();
            result.put(key, value.doubleValue()/total.doubleValue()*100);
        }

        result.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry->{
                    result2.put(entry.getKey(), entry.getValue());
                });

        return result2;
    }


    private SearchResponse searchLecturesWithAggregations(String academicNumber, String professorName) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 검색 쿼리 설정
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("academic_number", academicNumber))
                .must(QueryBuilders.termQuery("professor_name", professorName)));

        // 집계 설정
        searchSourceBuilder.aggregation(AggregationBuilders.terms("skill_ratio")
                .field("skill")
                .size(10));

        searchSourceBuilder.aggregation(AggregationBuilders.terms("level_ratio")
                .field("level")
                .size(10));

        searchSourceBuilder.aggregation(AggregationBuilders.terms("grade_ratio")
                .field("grade")
                .size(10));

        SearchRequest searchRequest = new SearchRequest(INDEX).source(searchSourceBuilder);

        // Elasticsearch에 질의 요청
        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

        return searchResponse;
    }



}
