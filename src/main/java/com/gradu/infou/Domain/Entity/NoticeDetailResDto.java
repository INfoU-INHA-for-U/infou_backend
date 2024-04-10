package com.gradu.infou.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class NoticeDetailResDto {
    private String id;
    private String title;
    private String content;
    private String link;
    private LocalDateTime localDateTime;
}
