package com.gradu.infou.Service;

import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Dto.Controller.PortalSearchAggregationResult;

import com.gradu.infou.Domain.Dto.Controller.SearchCondition;
import com.gradu.infou.Domain.Dto.Service.PortalDocumentResponseDto;
import com.gradu.infou.Domain.Dto.Service.PortalResponseDto;
import com.gradu.infou.Domain.Dto.Service.SearchLectureResDto;
import com.gradu.infou.Domain.Entity.Portal;
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
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.gradu.infou.Config.BaseResponseStatus.RESPONSE_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortalService {

    private final PortalRepository portalRepository;
    private final PortalProfessorRepository portalProfessorRepository;
    private final RestHighLevelClient elasticsearchClient;
    private final PortalDocumentRepository portalDocumentRepository;



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
//
//
//
//    public PortalDocumentResponseDto searchByAcademicNumber(String academicNumber){
//
//        List<PortalDocument> portalDocuments = portalDocumentRepository.findAllByAcademicNumber(academicNumber);
//
//        if(portalDocuments.isEmpty()) throw new BaseException(RESPONSE_ERROR);
//
//        return PortalDocumentResponseDto.fromEntities(portalDocuments);
//    }




}
