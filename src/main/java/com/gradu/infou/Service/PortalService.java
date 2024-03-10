package com.gradu.infou.Service;

import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Dto.Controller.PortalSearchAggregationResult;

import com.gradu.infou.Domain.Dto.Service.PortalDocumentResponseDto;
import com.gradu.infou.Domain.Entity.PortalDocument;
import com.gradu.infou.Repository.PortalProfessorRepository;

import com.gradu.infou.Repository.portal.PortalDocumentRepository;
import com.gradu.infou.Repository.portal.PortalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.gradu.infou.Config.BaseResponseStatus.RESPONSE_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortalService {

    private final PortalRepository portalRepository;
    private final PortalProfessorRepository portalProfessorRepository;
    private final PortalDocumentRepository portalDocumentRepository;
    private final RestHighLevelClient elasticsearchClient;


//    @Transactional(readOnly = true)
//    public List<PortalResponseDto> searchByLectureName(String major, String lectureName) {
//        List<Portal> portals = portalRepository.findAllByDepartmentAndLectureName(major, lectureName)
//                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PORTAL_FROM_LECTURE_NAME));
//
//        return portals.stream()
//                .map(PortalResponseDto::fromEntity)
//                .toList();
//    }
//    @Transactional(readOnly = true)
//    public List<PortalResponseDto> searchByProfessorName(String major, String professorName) {
//        List<Portal> portals = portalRepository.findAllByDepartmentAndProfessorName(major, professorName)
//                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PORTAL_FROM_PROFESSOR_NAME));
//
//        return portals.stream()
//                .map(PortalResponseDto::fromEntity)
//                .toList();
//    }
//    @Transactional(readOnly = true)
//    public Slice<PortalResponseDto> searchSliceByCondition(SearchCondition condition, Pageable pageable) {
//        return portalRepository.findSliceByCondition(condition, pageable);
//    }

    @Transactional(readOnly = true)
    public List<PortalSearchAggregationResult> searchSliceByCondition(String keyword, Condition condition, Pageable pageable) throws IOException {
        String[] sort = pageable.getSort().toString().split(": ");
        log.info("1: "+sort[0]);
        log.info("2: "+sort[1]);
        SearchResponse searchResponse = searchWithAggregations(keyword, condition, sort, pageable);

        Terms termsAggregation = searchResponse.getAggregations().get("group_by_fields");

        List<PortalSearchAggregationResult> results = new ArrayList<>();

        int i=0;
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber();

        for (Terms.Bucket bucket : termsAggregation.getBuckets()) {
            if(i++<size*page) continue;
            String key = bucket.getKeyAsString(); // 버킷의 키
            String[] s = key.split("/");

            Avg averageOption = bucket.getAggregations().get("average_"+sort[0]);
            double avgValue = averageOption.getValue(); // 평균 값

            PortalSearchAggregationResult result = new PortalSearchAggregationResult(s[0],s[1],s[2],s[3], avgValue);

            results.add(result);
        }

        return results;
    }

    public PortalDocumentResponseDto searchByAcademicNumber(String academicNumber){

        List<PortalDocument> portalDocuments = portalDocumentRepository.findAllByAcademicNumber(academicNumber);

        if(portalDocuments.isEmpty()) throw new BaseException(RESPONSE_ERROR);

        return PortalDocumentResponseDto.fromEntities(portalDocuments);
    }

    private SearchResponse searchWithAggregations(String keyword, Condition condition, String[] sort, Pageable pageable) throws IOException {

        int totalSize = pageable.getPageSize() * (pageable.getPageNumber() + 1);
        boolean asc = true;

        if(sort[1].equals("desc")||sort[1].equals("DESC")) asc = false;

        TermsAggregationBuilder aggregation = AggregationBuilders.terms("group_by_fields")
                .script(new Script(ScriptType.INLINE, "painless", "doc['academic_number'].value + '/' + doc['lecture_name'].value + '/' + doc['professor_name'].value + '/' + doc['department'].value", new HashMap<>()))
                .order(BucketOrder.aggregation("average_"+sort[0], asc))
                .subAggregation(AggregationBuilders.avg("average_"+sort[0]).field(sort[0])).size(totalSize);

//        log.info("total: {}",totalSize);


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 검색 쿼리 설정
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery(condition.name(), keyword)));

        // 집계 설정
        searchSourceBuilder.aggregation(aggregation);

        SearchRequest searchRequest = new SearchRequest("lecture_portal"); // index name
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

        return search;
    }

}
