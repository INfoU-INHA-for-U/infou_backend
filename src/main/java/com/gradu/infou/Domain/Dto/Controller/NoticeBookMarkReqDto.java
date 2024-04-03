package com.gradu.infou.Domain.Dto.Controller;

import io.netty.channel.ChannelHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NoticeBookMarkReqDto {
    @Schema(description = "notice id를 입력하세요")
    private String noticeId;
}
