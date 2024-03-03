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
    @Field(type = FieldType.Text)
    private String lectureName;
    @Field(type = FieldType.Text)
    private String department;
    @NotBlank
    @Field(type = FieldType.Text)
    private String semester;
    @Field(type = FieldType.Text)
    private String professorName;
    @NotBlank
    @Field(type = FieldType.Text)
    private String academicNumber;
    @NotBlank
    @Field(type = FieldType.Text)
    private String grade;
    @NotBlank
    @Field(type = FieldType.Text)
    private String skill;
    @NotBlank
    @Field(type = FieldType.Text)
    private String level;
    @PositiveOrZero
    @Field(type = FieldType.Double)
    private Double score;
    @Size(min=30, max=1000)
    @Field(type = FieldType.Text)
    private String review;


}
