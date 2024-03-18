package com.gradu.infou.Controller;

import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Dto.Controller.Kind;
import com.gradu.infou.Domain.Dto.Service.SearchLectureResDto;
import com.gradu.infou.Domain.Entity.PortalDocument;
import com.gradu.infou.Service.Portal2Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/api/v2/portals")
@RestController
@RequiredArgsConstructor
@Tag(name = "Portal2", description = "infou 서비스의 portal 강의평 관련입니다.")
public class Portal2Controller {
    private final Portal2Service portal2Service;

    @Operation(
            summary = "강의평 상세 조회 api",
            description = "강의평을 상세 조회하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="an", description = "학수번호를 입력합니다."),
                    @Parameter(name="professor", description = "교수명을 입력합니다.")
            }
    )
    @GetMapping("/detail")
    public BaseResponse<List<PortalDocument>> PortalDetail(@RequestParam("an") String academicNumber, @RequestParam("professor") String professor){

        List<PortalDocument> res = portal2Service.searchDetail(academicNumber, professor);

        return new BaseResponse<>(res);
    }

    @Operation(
            summary = "강의평 검색 api",
            description = "강의평을 검색하는 api입니다. \n\n" +
                    "keyword: 검색어 \n\n"+
                    "pageable의 sort: {option_1,asc}/{option_2,desc}/{option_3,asc}/{option_4,asc}/{option_5,asc} \n\n {option_1,asc}: option_1을 기준으로 오름차순 정렬합니다. \n\n"+
                    "ex) \"sort\": [\"option_1,asc\"]"
            ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @GetMapping("/search")
    public BaseResponse<List<SearchLectureResDto>> PortalSearch(@RequestParam("keyword") String keyword, @RequestParam("condition") Kind condition, Pageable pageable) throws IOException {

        List<SearchLectureResDto> results = portal2Service.searchSliceByCondition(keyword,condition,pageable);

        return new BaseResponse(results);
    }
}
