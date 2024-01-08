package com.gradu.infou.Service;

import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.SearchCondition;
import com.gradu.infou.Domain.Dto.Service.PortalResponseDto;
import com.gradu.infou.Domain.Entity.Portal;
import com.gradu.infou.Domain.Entity.PortalProfessor;
import com.gradu.infou.Repository.PortalProfessorRepository;
import com.gradu.infou.Repository.portal.PortalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PortalService {

    private final PortalRepository portalRepository;
    private final PortalProfessorRepository portalProfessorRepository;

    public List<PortalResponseDto> searchByLectureName(String major, String lectureName) {
        List<Portal> portals = portalRepository.findAllByDepartmentAndLectureName(major, lectureName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PORTAL_FROM_LECTURE_NAME));

        return portals.stream()
                .map(PortalResponseDto::fromEntity)
                .toList();
    }

    public List<PortalResponseDto> searchByProfessorName(String major, String professorName) {
        List<Portal> portals = portalRepository.findAllByDepartmentAndProfessorName(major, professorName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PORTAL_FROM_PROFESSOR_NAME));

        return portals.stream()
                .map(PortalResponseDto::fromEntity)
                .toList();
    }

    public Slice<PortalResponseDto> searchSliceByCondition(SearchCondition condition, Pageable pageable) {
        return portalRepository.findSliceByCondition(condition, pageable);
    }
}
