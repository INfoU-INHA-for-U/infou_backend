package com.gradu.infou.Domain.Dto.Service;

import com.gradu.infou.Domain.Entity.NoticeDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class NoticeList {
    String id;
    String title;
    String tags;
    LocalDateTime localDateTime;

    public static NoticeList toNoticeList(NoticeDocument noticeDocument){
        return NoticeList.builder()
                .id(noticeDocument.getId())
                .title(noticeDocument.getTitle())
                .localDateTime(noticeDocument.getDate())
                .tags(noticeDocument.getTags())
                .build();
    }
}
