package com.gradu.infou.Domain.Dto.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PortalSearchAggregationResult {
    private String academicNumber;
    private String lectureName;
    private String professorName;
    private String department;
    private double averageValue;
}
