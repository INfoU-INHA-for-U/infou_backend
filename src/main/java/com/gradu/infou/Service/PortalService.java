package com.gradu.infou.Service;

import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.SearchCondition;
//import com.gradu.infou.Domain.Dto.Service.PortalDetailDto;
//import com.gradu.infou.Domain.Dto.Service.PortalDocumentResponseDto;
import com.gradu.infou.Domain.Dto.Service.PortalDocumentResponseDto;
import com.gradu.infou.Domain.Dto.Service.PortalResponseDto;
import com.gradu.infou.Domain.Entity.Portal;
//import com.gradu.infou.Domain.Entity.PortalDocument;
import com.gradu.infou.Domain.Entity.PortalDocument;
import com.gradu.infou.Domain.Entity.PortalProfessor;
import com.gradu.infou.Repository.PortalProfessorRepository;
//import com.gradu.infou.Repository.portal.PortalDocumentRepository;
import com.gradu.infou.Repository.portal.PortalDocumentRepository;
import com.gradu.infou.Repository.portal.PortalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gradu.infou.Config.BaseResponseStatus.RESPONSE_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortalService {

    private final PortalRepository portalRepository;
    private final PortalProfessorRepository portalProfessorRepository;
    private final PortalDocumentRepository portalDocumentRepository;

    @Transactional(readOnly = true)
    public List<PortalResponseDto> searchByLectureName(String major, String lectureName) {
        List<Portal> portals = portalRepository.findAllByDepartmentAndLectureName(major, lectureName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PORTAL_FROM_LECTURE_NAME));

        return portals.stream()
                .map(PortalResponseDto::fromEntity)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<PortalResponseDto> searchByProfessorName(String major, String professorName) {
        List<Portal> portals = portalRepository.findAllByDepartmentAndProfessorName(major, professorName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PORTAL_FROM_PROFESSOR_NAME));

        return portals.stream()
                .map(PortalResponseDto::fromEntity)
                .toList();
    }
    @Transactional(readOnly = true)
    public Slice<PortalResponseDto> searchSliceByCondition(SearchCondition condition, Pageable pageable) {
        return portalRepository.findSliceByCondition(condition, pageable);
    }

    public PortalDocumentResponseDto searchByAcademicNumber(String academicNumber){

        List<PortalDocument> portalDocuments = portalDocumentRepository.findAllByAcademicNumber(academicNumber);

        if(portalDocuments.isEmpty()) throw new BaseException(RESPONSE_ERROR);

        return PortalDocumentResponseDto.fromEntities(portalDocuments);
    }


}
