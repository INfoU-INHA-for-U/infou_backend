package com.gradu.infou.Domain.Dto.Controller;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoticeListReqDto {
    private String type;
    private String tag;
}
