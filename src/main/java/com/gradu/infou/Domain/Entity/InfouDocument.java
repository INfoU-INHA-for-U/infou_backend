package com.gradu.infou.Domain.Entity;

import lombok.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.DoubleUnaryOperator;

@Document(indexName = "lecture_infou")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class InfouDocument {
    @Id
    private String id;
    @Field(name = "@timestamp",  format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss", type = FieldType.Date)
    private LocalDateTime timestamp;
    @Field(name="lecture_name", type = FieldType.Text)
    private String lectureName;
    @Field(name="lecture_type", type = FieldType.Text)
    private String lectureType;
    @Field(name="department", type = FieldType.Text)
    private String department;
    @Field(type = FieldType.Text)
    private String semester;
    @Field(name="professor_name", type = FieldType.Text)
    private String professorName;
    @Field(name="academic_number", type = FieldType.Text)
    private String academicNumber;
    @Field(type = FieldType.Text)
    private String grade;
    @Field(type = FieldType.Text)
    private String skill;
    @Field(type = FieldType.Text)
    private String level;
    @Field(name="score", type = FieldType.Double)
    private Double score;
    @Field(type = FieldType.Text)
    private String review;
    @Field(type = FieldType.Integer)
    private Integer agree;
    @Field(type = FieldType.Integer)
    private Integer disagree;


}
