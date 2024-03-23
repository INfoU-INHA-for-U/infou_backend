package com.gradu.infou.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Document(indexName = "notice_bookmark")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class NoticeBookmarkDocument {
    @Id
    @Field(name="id", type = FieldType.Text)
    private String id;
    @Field(name="user_id", type = FieldType.Text)
    private String userId;
    @Field(name="notice_id", type = FieldType.Text)
    private String noticeDocumentId;
    @Field(name="notice_title", type = FieldType.Text)
    private String noticeTitle;
    @Field(name="notice_href", type = FieldType.Text)
    private String noticeHref;
    @Field(name = "@timestamp",  format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss", type = FieldType.Date)
    private LocalDateTime time;
    @Field(name = "notice_date",  format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss", type = FieldType.Date)
    private LocalDateTime noticeDate;
    @Field(name="notice_tags", type = FieldType.Text)
    private String noticeTags;
    @Field(name="notice_type", type = FieldType.Text)
    private String noticeType;

    public static NoticeBookmarkDocument toNoticeBookmark(User user, NoticeDocument noticeDocument){
        return NoticeBookmarkDocument
                .builder()
                .userId(user.getId().toString())
                .noticeDocumentId(noticeDocument.getId())
                .noticeDate(noticeDocument.getDate())
                .noticeHref(noticeDocument.getHref())
                .noticeTags(noticeDocument.getTags())
                .noticeTitle(noticeDocument.getTitle())
                .noticeType(noticeDocument.getType())
                .time(LocalDateTime.now(ZoneId.of("GMT")))
                .build();
    }
}
