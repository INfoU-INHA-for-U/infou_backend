package com.gradu.infou.converter;

import com.gradu.infou.Domain.Dto.Controller.AddInfouReqDto;
import com.gradu.infou.Domain.Entity.InfouDocument;
import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

public class InfouConverter {
    public static InfouDocument toInfouDocument(String userId, AddInfouReqDto addInfouReqDto){
        return InfouDocument.builder()
                .timestamp(LocalDateTime.now(ZoneId.of("GMT")))
                .userId(userId)
                .lectureName(addInfouReqDto.getLectureName())
                .lectureType(addInfouReqDto.getLectureType())
                .department(addInfouReqDto.getDepartment())
                .professorName(addInfouReqDto.getProfessorName())
                .academicNumber(addInfouReqDto.getAcademicNumber())
                .semester(addInfouReqDto.getSemester())
                .grade(addInfouReqDto.getGrade())
                .skill(addInfouReqDto.getSkill())
                .level(addInfouReqDto.getLevel())
                .score(addInfouReqDto.getScore())
                .review(addInfouReqDto.getReview())
                .agree(0)
                .disagree(0)
                .build();
    }
}
