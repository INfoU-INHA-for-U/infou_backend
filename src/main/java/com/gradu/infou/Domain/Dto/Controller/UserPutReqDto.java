package com.gradu.infou.Domain.Dto.Controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserPutReqDto {
    @Schema(description = "닉네임")
    private String name;
    @Schema(description = "학년")
    private String grade;
    @Schema(description = "학과")
    private String major;
    @Schema(description = "학과 선택")
    private List<String> selectNotice;
}
