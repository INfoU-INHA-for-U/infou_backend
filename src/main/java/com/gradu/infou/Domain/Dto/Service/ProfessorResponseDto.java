package com.gradu.infou.Domain.Dto.Service;

import com.gradu.infou.Domain.Entity.Professor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@ToString
public class ProfessorResponseDto {
    private String name;

    @Builder
    private ProfessorResponseDto(String name) {
        this.name = name;
    }

    public static ProfessorResponseDto fromEntity(Professor professor){
        return ProfessorResponseDto.builder()
                .name(professor.getName())
                .build();
    }
}
