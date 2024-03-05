package com.gradu.infou.Domain.Dto.Service;

import com.gradu.infou.Domain.Entity.InfouDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class InfouDetailResDto {
    private Long total;
    private HashMap<String, Double> skillRatio;
    private HashMap<String, Double> gradeRatio;
    private HashMap<String, Double> levelRatio;

    private Page<InfouDocument> infouDocuments;

    public static InfouDetailResDto fromEntity(Long total, HashMap<String, Double> level, HashMap<String, Double> skill, HashMap<String, Double> grade, Page<InfouDocument> infouDocuments){
        return InfouDetailResDto.builder()
                .total(total)
                .levelRatio(level)
                .skillRatio(skill)
                .gradeRatio(grade)
                .infouDocuments(infouDocuments)
                .build();
    }
}
