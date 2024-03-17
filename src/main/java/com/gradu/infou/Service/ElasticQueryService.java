package com.gradu.infou.Service;

import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Dto.Controller.PortalSearchAggregationResult;
import com.gradu.infou.Domain.Dto.Service.SearchLectureResDto;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticQueryService {

    private final RestHighLevelClient elasticsearchClient;


    public SearchResponse searchWithAggregations(String keyword, Condition condition, String[] sort, Pageable pageable, String index) throws IOException {

        int totalSize = pageable.getPageSize() * (pageable.getPageNumber() + 1);
        boolean asc = true;

        if(sort[1].equals("desc")||sort[1].equals("DESC")) asc = false;

        TermsAggregationBuilder aggregation = AggregationBuilders.terms("group_by_fields")
                .script(new Script(ScriptType.INLINE, "painless", "doc['academic_number'].value + '/' + doc['lecture_name'].value + '/' + doc['professor_name'].value + '/' + doc['department'].value", new HashMap<>()))
                .order(BucketOrder.aggregation("average_"+sort[0], asc))
                .subAggregation(AggregationBuilders.avg("average_"+sort[0]).field(sort[0])).size(totalSize);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 검색 쿼리 설정
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery(condition.name(), keyword)));

        // 집계 설정
        searchSourceBuilder.aggregation(aggregation);

        SearchRequest searchRequest = new SearchRequest(index); // index name
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

        return search;
    }

    public List<SearchLectureResDto> searchLecture(String keyword, Condition condition, Pageable pageable, String index) throws IOException {
        String[] sort = pageable.getSort().toString().split(": ");
        log.info("1: "+sort[0]);
        log.info("2: "+sort[1]);
        SearchResponse searchResponse = searchWithAggregations(keyword, condition, sort, pageable, index);

        Terms termsAggregation = searchResponse.getAggregations().get("group_by_fields");

        List<SearchLectureResDto> results = new ArrayList<>();

        int i=0;
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber();

        for (Terms.Bucket bucket : termsAggregation.getBuckets()) {
            if(i++<size*page) continue;
            String key = bucket.getKeyAsString(); // 버킷의 키
            String[] s = key.split("/");

            Avg averageOption = bucket.getAggregations().get("average_"+sort[0]);
            double avgValue = averageOption.getValue(); // 평균 값

            SearchLectureResDto result = new SearchLectureResDto(s[0],s[1],s[2],s[3], avgValue);

            results.add(result);
        }
        return results;
    }
}
