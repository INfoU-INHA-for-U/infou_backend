package com.gradu.infou.Service;

import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Dto.Controller.Kind;
import com.gradu.infou.Domain.Dto.Service.SearchLectureResDto;
import com.gradu.infou.Domain.Entity.PortalDocument;
import com.gradu.infou.Repository.portal.PortalDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static com.gradu.infou.Config.BaseResponseStatus.RESPONSE_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class Portal2Service {
    private final ElasticQueryService elasticQueryService;
    private final PortalDocumentRepository portalDocumentRepository;



    @Transactional(readOnly = true)
    public List<SearchLectureResDto> searchSliceByCondition(String keyword, Kind condition, Pageable pageable) throws IOException {

        List<SearchLectureResDto> lectureResults = elasticQueryService.searchLecture(keyword, condition, pageable, "lecture_portal");

        return lectureResults;
    }

    public List<PortalDocument> searchDetail(String academicNumber, String professor){

        List<PortalDocument> res = portalDocumentRepository.findAllByAcademicNumberAndProfessorName(academicNumber, professor);

        if(res.isEmpty()) throw new BaseException(RESPONSE_ERROR);

        return res;
    }


}
