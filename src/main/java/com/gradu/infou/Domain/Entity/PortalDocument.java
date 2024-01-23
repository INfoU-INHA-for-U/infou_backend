package com.gradu.infou.Domain.Entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "portal")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PortalDocument {
    @Id
    @org.springframework.data.annotation.Id
    @Field(name = "_id", type = FieldType.Keyword)
    private Long id;
    @Field(name = "lecture_name", type = FieldType.Text)
    private String lectureName;
    //professor_name:서영덕 department:컴퓨터공학과 semester:202201 academic_number:CSE3207 lecture_type:전선 survey_cnt:12 total_cnt:19 option_1:5 option_2:5 option_3:4.9 option_4:5 option_5:5 _id:6243 _type:_doc _index:portal _score:0
    @Field(name = "professor_name", type = FieldType.Text)
    private String professorName;

    @Field(name = "department", type = FieldType.Text)
    private String department;

    @Field(name = "semester", type = FieldType.Text)
    private String semester;

    @Field(name = "academic_number", type = FieldType.Text)
    private String academicNumber;

    @Field(name = "lecture_type", type = FieldType.Text)
    private String lectureType;

    @Field(name = "survey_cnt", type = FieldType.Text)
    private String surveyCnt;

    @Field(name = "total_cnt", type = FieldType.Text)
    private String totalCnt;

    @Field(name = "option_1", type = FieldType.Double)
    private Double option_1;

    @Field(name = "option_2", type = FieldType.Double)
    private Double option_2;

    @Field(name = "option_3", type = FieldType.Double)
    private Double option_3;

    @Field(name = "option_4", type = FieldType.Double)
    private Double option_4;

    @Field(name = "option_5", type = FieldType.Double)
    private Double option_5;
//    private String department;
//    private String semester;
//    private String academicNumber;
//    private String lectureName;
//    private String lectureType;
//    private String professorName;
//    private String surveyCnt;
//    private String totalCnt;
//    private Double option_1;
//    private Double option_2;
//    private Double option_3;
//    private Double option_4;
//    private Double option_5;
}
