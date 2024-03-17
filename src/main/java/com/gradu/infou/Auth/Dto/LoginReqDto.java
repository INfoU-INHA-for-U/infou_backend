package com.gradu.infou.Auth.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LoginReqDto {
    @NotBlank
    @Schema(description = "회원 정보를 입력합니다. authId: 구글 로그인하고 얻는 id 정보")
    private String authId;

}
