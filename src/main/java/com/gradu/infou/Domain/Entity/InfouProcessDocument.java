package com.gradu.infou.Domain.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "lecture_infou_process")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InfouProcessDocument {
    @Id
    private String id;
    @Field(name="academic_number", type = FieldType.Text)
    private String academicNumber;
    @Field(name="lecture_name", type = FieldType.Text)
    private String lectureName;
    @Field(name="lecture_type", type = FieldType.Text)
    private String lectureType;
    @Field(name="professor_name", type = FieldType.Text)
    private String professorName;
    @Field(name="department", type = FieldType.Text)
    private String department;
    @Field(name="average_value", type = FieldType.Double)
    private double averageValue;
    @Field(name = "sum_value", type = FieldType.Double)
    private double sumValue;
    @Field(name="count", type = FieldType.Integer)
    private Integer count;


    public static InfouProcessDocument toInfouProcessDocument(InfouDocument infouDocument, Double score, Double sum, Integer count){
        return InfouProcessDocument.builder()
                .academicNumber(infouDocument.getAcademicNumber())
                .lectureName(infouDocument.getLectureName())
                .lectureType(infouDocument.getLectureType())
                .professorName(infouDocument.getProfessorName())
                .department(infouDocument.getDepartment())
                .averageValue(score)
                .sumValue(sum)
                .count(count)
                .build();
    }
}
