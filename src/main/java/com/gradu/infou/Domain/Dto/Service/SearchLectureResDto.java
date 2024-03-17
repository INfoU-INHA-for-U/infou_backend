package com.gradu.infou.Domain.Dto.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchLectureResDto {
    private String academicNumber;
    private String lectureName;
    private String professorName;
    private String department;
    private double averageValue;
}
