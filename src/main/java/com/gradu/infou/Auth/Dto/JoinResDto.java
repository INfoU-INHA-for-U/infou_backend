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
    private String token;

    public static JoinResDto fromEntity(String token){
        return JoinResDto.builder()
                .token(token)
                .build();
    }
}
