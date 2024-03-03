package com.gradu.infou.Domain.Dto.Service;

//import com.gradu.infou.Domain.Entity.PortalDocument;
import com.gradu.infou.Domain.Entity.PortalDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortalDocumentResponseDto {

    private String department;
    private String academicNumber;
    private String lectureName;
    private String lectureType;
    private List<String> professorName;
    private List<PortalDetailDto> portal;

    public static PortalDocumentResponseDto fromEntities(List<PortalDocument> portalDocuments){
        List<PortalDetailDto> portalDetailDtos = PortalDetailDto.fromEntities(portalDocuments);

        return PortalDocumentResponseDto.builder()
                .department(portalDocuments.get(0).getDepartment())
                .lectureType(portalDocuments.get(0).getLectureType())
                .academicNumber(portalDocuments.get(0).getAcademicNumber())
                .lectureName(portalDocuments.get(0).getLectureName())
                .professorName(Arrays.stream(portalDocuments.get(0).getProfessorName().split(", ")).toList())
                .portal(portalDetailDtos)
                .build();
    }

}

