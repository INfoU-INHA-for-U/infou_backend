package com.gradu.infou.Auth.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class JoinResDto {
    private String accessToken;
    private String refreshToken;

    public static JoinResDto fromEntity(String accessToken, String refreshToken){
        return JoinResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
