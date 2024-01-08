package com.gradu.infou.Domain.Dto.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCondition {
    private String major;
    private List<String> lectureNames = new ArrayList<>();
}
