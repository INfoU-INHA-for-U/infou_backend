package com.gradu.infou.Auth.Dto;

import com.gradu.infou.Domain.Entity.Enum.Role;
import com.gradu.infou.Domain.Entity.User;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinReqDto {
    private String authId;
    private String email;
    private String name;
    //private Role role;


    public User toUserEntity(){
        return User.builder()
                .authId(authId)
                .email(email)
                .name(name)
                .role(Role.USER.name())
                .reward(0L)
                .build();
    }
}
