package com.gradu.infou.Controller;

import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Dto.Controller.PortalSearchAggregationResult;
import com.gradu.infou.Domain.Dto.Controller.SearchCondition;
import com.gradu.infou.Domain.Dto.Service.PortalDocumentResponseDto;
import com.gradu.infou.Domain.Dto.Service.PortalResponseDto;
import com.gradu.infou.Domain.Dto.Service.SearchLectureResDto;
import com.gradu.infou.Domain.Entity.PortalDocument;
import com.gradu.infou.Service.PortalService;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/api/v1/portals")
@RestController
@RequiredArgsConstructor
@Tag(name = "Portal", description = "portal 강의평 관련입니다.")
public class PortalController {

    private final PortalService portalService;

//    @Operation(
//            summary = "강의평 검색 api(수정 필요)",
//            description = "강의평을 검색하는 api입니다.",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
//                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
//                    })
//            }
////            ,
////            parameters = {
////                    @Parameter(name="pageable", description = "페이징 관련 정보를 입력합니다. (sort: 비워두면 score 기준으로 내림차순 정렬됩니다.)", required = true)
////            }
//    )
//    @GetMapping("/search")
//    public BaseResponse<List<PortalResponseDto>> PortalSearch(@RequestParam("major") String major, @RequestParam("keyword") String keyword, @RequestParam("condition") Condition condition) {
//
//        List<PortalResponseDto> res = null;
//
//        if (condition.equals(Condition.name)) {
//            res = portalService.searchByLectureName(major, keyword);
//        } else if (condition.equals(Condition.professor)) {
//            res = portalService.searchByProfessorName(major, keyword);
//        }
//
//        return new BaseResponse < List<PortalResponseDto>> (res);
//    }
//
//    @Operation(
//            summary = "강의평 검색 api(수정 필요)",
//            description = "강의평을 검색하는 api입니다.",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
//                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
//                    })
//            }
//    )
//    @GetMapping("/total")
//    public BaseResponse<Slice<PortalResponseDto>> PortalTotal(@Nullable SearchCondition searchCondition, @Nullable Pageable pageable){ //page default가 size=10, page=0이기 때문에, 따로 설정 안함. 할 경우, @PageableDefault(size=10, page=0), 이런 식으로 작성
//
//        if(pageable.getPageSize()>100){
//            throw new BaseException(BaseResponseStatus.SIZE_TOO_BIG);
//        }
//
//        Slice<PortalResponseDto> res = portalService.searchSliceByCondition(searchCondition, pageable);
//
//        return new BaseResponse<Slice<PortalResponseDto>>(res);
//    }


}
