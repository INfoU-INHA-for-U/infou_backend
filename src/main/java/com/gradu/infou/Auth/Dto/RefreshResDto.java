package com.gradu.infou.Auth.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshResDto {
    private String refreshToken;
}
