package com.gradu.infou.Controller;

import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Dto.Controller.SearchCondition;
import com.gradu.infou.Domain.Dto.Service.PortalDocumentResponseDto;
import com.gradu.infou.Domain.Dto.Service.PortalResponseDto;
import com.gradu.infou.Domain.Entity.PortalDocument;
import com.gradu.infou.Service.PortalService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/portals")
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

    @GetMapping("/total")
    public BaseResponse<Slice<PortalResponseDto>> PortalTotal(@Nullable SearchCondition searchCondition, @Nullable Pageable pageable){ //page default가 size=10, page=0이기 때문에, 따로 설정 안함. 할 경우, @PageableDefault(size=10, page=0), 이런 식으로 작성

        if(pageable.getPageSize()>100){
            throw new BaseException(BaseResponseStatus.SIZE_TOO_BIG);
        }

        Slice<PortalResponseDto> res = portalService.searchSliceByCondition(searchCondition, pageable);

        return new BaseResponse<Slice<PortalResponseDto>>(res);
    }

    @GetMapping("/detail")
    public BaseResponse<PortalDocumentResponseDto> PortalDetail(@RequestParam("an") String academicNumber){

        PortalDocumentResponseDto res = portalService.searchByAcademicNumber(academicNumber);

        return new BaseResponse<PortalDocumentResponseDto>(res);
    }
}
