package com.gradu.infou.Domain.Dto.Service;

import com.gradu.infou.Domain.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserDetailResDto {
    private String name;
    private Long reward;
    private List<String> selectNotice;
    private Long review;

    public static UserDetailResDto toUserDetailResDto(User user){
        return UserDetailResDto.builder()
                .name(user.getName())
                .reward(user.getReward())
                .selectNotice(user.getSelectNotice())
                .review(user.getReview())
                .build();
    }
}
