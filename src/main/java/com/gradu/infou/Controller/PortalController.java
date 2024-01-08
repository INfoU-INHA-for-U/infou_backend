package com.gradu.infou.Controller;

import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Dto.Controller.PortalSearchDto;
import com.gradu.infou.Domain.Dto.Service.PortalResponseDto;
import com.gradu.infou.Domain.Entity.PortalProfessor;
import com.gradu.infou.Service.PortalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/v1/api/portals")
@RestController
@RequiredArgsConstructor
public class PortalController {

    private final PortalService portalService;

    @GetMapping("/search")
    public BaseResponse<List<PortalResponseDto>> PortalSearch(@RequestParam("major") String major, @RequestParam("keyword") String keyword, @RequestParam("condition") Condition condition) {

        List<PortalResponseDto> res = null;

        if (condition.equals(Condition.name)) {
            res = portalService.searchByLectureName(major, keyword);
        } else if (condition.equals(Condition.professor)) {
            res = portalService.searchByProfessorName(major, keyword);
        }

        return new BaseResponse < List<PortalResponseDto>> (res);
    }
}
