package com.gradu.infou.Auth.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResDto {
    private String token;

    public static LoginResDto fromEntity(String token){
        return LoginResDto.builder()
                .token(token)
                .build();
    }
}
