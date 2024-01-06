package com.gradu.infou.Service;

import com.gradu.infou.Domain.Dto.Service.PortalResponseDto;
import com.gradu.infou.Domain.Entity.PortalProfessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PortalServiceTest {

    @Autowired
    PortalService portalService;

    @Test
    void searchByLectureName() {
        // given
        String major = "컴퓨터공학과";
        String lectureName = "자료구조";

        // when
        List<PortalResponseDto> portalResponseDtos = portalService.searchByLectureName(major, lectureName);
        portalResponseDtos.forEach(portalResponseDto -> {
            log.info("portalResponseDto={}", portalResponseDto);
        });

        // then
//        assertEquals(portalProfessors.get(0).getLectureName(), lectureName);
    }

    @Test
    void searchByProfessorName() {
        // given
        String major = "컴퓨터공학과";
        String professorName = "김병형";

        // when
        List<PortalResponseDto> portalResponseDtos = portalService.searchByProfessorName(major, professorName);
        portalResponseDtos.forEach(portalResponseDto -> {
            log.info("portalResponseDto={}", portalResponseDto);
        });

        // then
    }

    @Test
    void searchByDepartment() {
        // given
        String department = "컴퓨터공학과";
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Slice<PortalProfessor> portalProfessors = portalService.searchByDepartment(department, pageRequest);
        log.info("portalProfessors={}", portalProfessors);
        log.info("portalProfessors.getContent()={}", portalProfessors.getContent());
        portalProfessors.getContent()
                        .forEach(portalProfessor -> {
                            log.info("portalProfessor={}", portalProfessor);
                            log.info("portalProfessor.getPortal().getId()={}", portalProfessor.getPortal().getId());
                            log.info("portalProfessor.getPortal().getLectureName()={}", portalProfessor.getPortal().getLectureName());
                            log.info("portalProfessor.getProfessor().getName()={}", portalProfessor.getProfessor().getName());
                        });
        log.info("portal.isFirst={}", portalProfessors.isFirst());
        log.info("portal.isLast={}", portalProfessors.isLast());
        log.info("portal.hasNext={}", portalProfessors.hasNext());
        log.info("portal.hasPrevious={}", portalProfessors.hasPrevious());
        log.info("portal.nextPageable={}", portalProfessors.nextPageable());
        log.info("portal.previousPageable={}", portalProfessors.previousPageable());
        log.info("portal.getNumber={}", portalProfessors.getNumber());
        log.info("portal.getNumberOfElements={}", portalProfessors.getNumberOfElements());
        log.info("portal.getSize={}", portalProfessors.getSize());


        // then
    }


}