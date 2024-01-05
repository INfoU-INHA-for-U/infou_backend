package com.gradu.infou.Service;

import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Entity.PortalProfessor;
import com.gradu.infou.Repository.PortalProfessorRepository;
import com.gradu.infou.Repository.PortalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortalService {

    private final PortalRepository portalRepository;
    private final PortalProfessorRepository portalProfessorRepository;

    public List<PortalProfessor> searchByLectureName(String major, String lectureName) {
        return portalProfessorRepository.findByLectureName(major, lectureName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PORTAL_FROM_LECTURE_NAME));
    }

    public List<PortalProfessor> searchByProfessorName(String major, String professorName) {
        return portalProfessorRepository.findByProfessorName(major, professorName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PORTAL_FROM_PROFESSOR_NAME));
    }
}
