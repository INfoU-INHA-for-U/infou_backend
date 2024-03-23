package com.gradu.infou.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "notice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoticeDocument {

    @Id
    @Field(name="_id", type = FieldType.Text)
    private String id;
    @Field(name="title", type = FieldType.Text)
    private String title;
    @Field(name = "date",  format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss", type = FieldType.Date)
    private LocalDateTime date;
    @Field(name="href", type = FieldType.Text)
    private String href;
    @Field(name="type", type = FieldType.Text)
    private String type;
    @Field(name="tags", type = FieldType.Text)
    private String tags;

}
