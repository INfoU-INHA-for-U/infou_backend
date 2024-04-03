package com.gradu.infou.Domain.Dto.Controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Valid
public class AddInfouReqDto {
    
    @NotBlank
    @Schema(description = "강의명")
    private String lectureName;
    @NotBlank
    @Schema(description = "강의 타입, ex) 전선, 전필, 교필 등")
    private String lectureType;
    @NotBlank
    @Schema(description = "학과")
    private String department;
    @NotBlank
    @Schema(description = "학기, ex) 202202, 202103 등")
    private String semester;
    @NotBlank
    @Schema(description = "교수명")
    private String professorName;
    @NotBlank
    @Schema(description = "학수번호, ex) ACE9708, ACE9704 등")
    private String academicNumber;
    @NotBlank
    @Schema(description = "수강 당시 학년, ex) 1학년, 2학년 등")
    private String grade;
    @NotBlank
    @Schema(description = "교수님 강의력, ex) 만족해요, 그저 그래요 등")
    private String skill;
    @NotBlank
    @Schema(description = "강의 난이도, ex) 쉬워요, 어렵진 않아요")
    private String level;
    @PositiveOrZero
    @Schema(description = "총점")
    private Double score;
    @Size(min=30, max=1000)
    @Schema(description = "리뷰 내용, 30~1000자 이내")
    private String review;


}
