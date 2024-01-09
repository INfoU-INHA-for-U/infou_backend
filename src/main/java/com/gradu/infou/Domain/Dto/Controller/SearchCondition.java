package com.gradu.infou.Domain.Dto.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCondition {
    private String major;
    private List<String> lectureNames = new ArrayList<>();
}
