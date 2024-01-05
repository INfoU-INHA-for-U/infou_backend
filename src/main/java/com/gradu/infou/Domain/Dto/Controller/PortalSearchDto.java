package com.gradu.infou.Domain.Dto.Controller;

import com.gradu.infou.Domain.Entity.PortalProfessor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortalSearchDto {
    private List<PortalProfessor> portalProfessorList;
}
