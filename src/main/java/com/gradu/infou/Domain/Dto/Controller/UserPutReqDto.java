package com.gradu.infou.Domain.Dto.Controller;

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
    private String name;
    private String grade;
    private String major;
    private List<String> selectNotice;
}
