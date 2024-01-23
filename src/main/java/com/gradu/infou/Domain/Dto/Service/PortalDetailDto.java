package com.gradu.infou.Domain.Dto.Service;

import com.gradu.infou.Domain.Entity.PortalDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortalDetailDto {
    private Long id;
    private String surveyCnt;
    private String semester;
    private String totalCnt;
    private Double option_1;
    private Double option_2;
    private Double option_3;
    private Double option_4;
    private Double option_5;

    public static PortalDetailDto fromEntity(PortalDocument portalDocument){
        return PortalDetailDto.builder()
                .id(portalDocument.getId())
                .option_1(portalDocument.getOption_1())
                .option_2(portalDocument.getOption_2())
                .option_3(portalDocument.getOption_3())
                .option_4(portalDocument.getOption_4())
                .option_5(portalDocument.getOption_5())
                .semester(portalDocument.getSemester())
                .surveyCnt(portalDocument.getSurveyCnt())
                .totalCnt(portalDocument.getTotalCnt())
                .build();
    }

    public static List<PortalDetailDto> fromEntities(List<PortalDocument> portalDocuments){
        return portalDocuments.stream()
                .map(PortalDetailDto::fromEntity)
                .toList();
    }
}
