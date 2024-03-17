package com.gradu.infou.Service;

import com.gradu.infou.Domain.Dto.Controller.SearchCondition;
import com.gradu.infou.Domain.Dto.Service.PortalResponseDto;
import com.gradu.infou.Domain.Entity.PortalProfessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class PortalServiceTest {

    @Autowired
    PortalService portalService;

//    @Test
//    void searchByLectureName() {
//        // given
//        String major = "컴퓨터공학과";
//        String lectureName = "자료구조";
//
//        // when
//        List<PortalResponseDto> portalResponseDtos = portalService.searchByLectureName(major, lectureName);
//        portalResponseDtos.forEach(portalResponseDto -> {
//            log.info("portalResponseDto={}", portalResponseDto);
//        });
//
//        // then
////        assertEquals(portalProfessors.get(0).getLectureName(), lectureName);
//    }
//
//    @Test
//    void searchByProfessorName() {
//        // given
//        String major = "컴퓨터공학과";
//        String professorName = "김병형";
//
//        // when
//        List<PortalResponseDto> portalResponseDtos = portalService.searchByProfessorName(major, professorName);
//        portalResponseDtos.forEach(portalResponseDto -> {
//            log.info("portalResponseDto={}", portalResponseDto);
//        });
//
//        // then
//    }
//
//    @Test
//    void searchSliceByCondition() throws IOException {
//        // given
//        String major = null;
//        List<String> lectureNames = new ArrayList<>();//List.of("자료구조", "객체지향프로그래밍 1", "생명과학");
//        SearchCondition searchCondition = new SearchCondition(major, lectureNames);
//
//        PageRequest pageRequest = PageRequest.of(0, 10);
//
//        // when
//        Slice<PortalResponseDto> portalResponseDtos = portalService.searchSliceByCondition(searchCondition, pageRequest);
//        portalResponseDtos.forEach(portalResponseDto -> {
//            log.info("portalResponseDto={}", portalResponseDto);
//        });
//
//        // then
//    }


}