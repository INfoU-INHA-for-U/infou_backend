package com.gradu.infou.Service;

import com.gradu.infou.Domain.Entity.PortalProfessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        List<PortalProfessor> portalProfessors = portalService.searchByLectureName(major, lectureName);
        portalProfessors.forEach(portalProfessor -> {
            log.info("portalProfessor={}", portalProfessor);
            log.info("portalProfessor.getPortal().getLectureName()={}", portalProfessor.getPortal().getLectureName());
            log.info("portalProfessor.getProfessor().getName()={}", portalProfessor.getProfessor().getName());
        });

        // then
//        assertEquals(portalProfessors.get(0).getLectureName(), lectureName);
    }


}