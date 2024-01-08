package com.gradu.infou.Domain.Dto.Service;

import com.gradu.infou.Domain.Entity.Portal;
import com.gradu.infou.Domain.Entity.Professor;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@ToString
public class PortalResponseDto {

//        "lecture_name": "이산수학",
//        "department": "컴퓨터공학과",
//        "academic_number": "ACE1312",
//        "survey_cnt": "68",
//        "total_cnt": "86",
//        "semester": "202102",
//        "option_1": "4.38",
//        "option_2": "4.19",
//        "option_3": "4.25",
//        "option_4": "4.19",
//        "option_5": "3.99",
//        "detail_uk": 20,
//        "professors": [
//    {
//        "name": "김병형"
//    }
//    ]

    private String lecture_name;
    private String department;
    private String academic_number;
    private String survey_cnt;
    private String total_cnt;
    private String semester;
    private String option_1;
    private String option_2;
    private String option_3;
    private String option_4;
    private String option_5;
    private Long detail_uk;
    private List<ProfessorResponseDto> professors = new ArrayList<>();

    @Builder
    private PortalResponseDto(String lectureName, String department, String academicNumber, String surveyCnt, String totalCnt, String semester, String option_1, String option_2, String option_3, String option_4, String option_5, Long detailUk) {
        this.lecture_name = lectureName;
        this.department = department;
        this.academic_number = academicNumber;
        this.survey_cnt = surveyCnt;
        this.total_cnt = totalCnt;
        this.semester = semester;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.option_4 = option_4;
        this.option_5 = option_5;
        this.detail_uk = detailUk;
    }

    @QueryProjection
    public PortalResponseDto(Portal portal) {
        this.lecture_name = portal.getLectureName();
        this.department = portal.getDepartment();
        this.academic_number = portal.getAcademicNumber();
        this.survey_cnt = portal.getSurveyCnt();
        this.total_cnt = portal.getTotalCnt();
        this.semester = portal.getSemester();
        this.option_1 = portal.getOption_1();
        this.option_2 = portal.getOption_2();
        this.option_3 = portal.getOption_3();
        this.option_4 = portal.getOption_4();
        this.option_5 = portal.getOption_5();
        this.detail_uk = portal.getDetailUk();

        portal.getPortalProfessors().forEach( portalProfessor -> {
            Professor professor = portalProfessor.getProfessor();
            ProfessorResponseDto professorResponseDto = ProfessorResponseDto.fromEntity(professor);
            this.professors.add(professorResponseDto);
        });
    }

    public static PortalResponseDto fromEntity(Portal portal){
        PortalResponseDto dto = PortalResponseDto.builder()
                .lectureName(portal.getLectureName())
                .department(portal.getDepartment())
                .academicNumber(portal.getAcademicNumber())
                .surveyCnt(portal.getSurveyCnt())
                .totalCnt(portal.getTotalCnt())
                .semester(portal.getSemester())
                .option_1(portal.getOption_1())
                .option_2(portal.getOption_2())
                .option_3(portal.getOption_3())
                .option_4(portal.getOption_4())
                .option_5(portal.getOption_5())
                .detailUk(portal.getDetailUk())
                .build();

        portal.getPortalProfessors().forEach( portalProfessor -> {
            Professor professor = portalProfessor.getProfessor();
            ProfessorResponseDto professorResponseDto = ProfessorResponseDto.fromEntity(professor);
            dto.professors.add(professorResponseDto);
        });

        return dto;
    }

    public void addProfessors(List<Professor> professors){
        for (Professor professor : professors) {
            this.professors.add(ProfessorResponseDto.fromEntity(professor));
        }
    }
}
