package com.gradu.infou.Domain.Dto.Controller;

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
    private String lectureName;
    @NotBlank
    private String lectureType;
    @NotBlank
    private String department;
    @NotBlank
    private String semester;
    @NotBlank
    private String professorName;
    @NotBlank
    private String academicNumber;
    @NotBlank
    private String grade;
    @NotBlank
    private String skill;
    @NotBlank
    private String level;
    @PositiveOrZero
    private Double score;
    @Size(min=30, max=1000)
    private String review;


}
