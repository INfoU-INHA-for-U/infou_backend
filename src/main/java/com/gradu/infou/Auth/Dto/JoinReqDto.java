package com.gradu.infou.Auth.Dto;

import com.gradu.infou.Domain.Entity.Enum.Role;
import com.gradu.infou.Domain.Entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinReqDto {
    @Schema(description = "구글 로그인하고 얻는 id 정보")
    private String authId;
    @Schema(description = "회원 정보를 입력합니다. \n authId: 구글 로그인하고 얻는 id 정보, \n email: '@inha.edu' 또는 '@inha.ac.kr'이어야 합니다.", required = true)
    private String email;
    private String name;
    private String grade;
    //private Role role;


    public User toUserEntity(){
        return User.builder()
                .authId(authId)
                .email(email)
                .name(name)
                .role(Role.USER.name())
                .grade(grade)
                .reward(0L)
                .build();
    }
}
