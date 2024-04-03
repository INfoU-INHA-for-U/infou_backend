package com.gradu.infou.Controller;

import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Domain.Dto.Controller.AddInfouReqDto;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Dto.Controller.Kind;
import com.gradu.infou.Domain.Dto.Service.InfouDetailResDto;
import com.gradu.infou.Domain.Dto.Service.SearchLectureResDto;
import com.gradu.infou.Domain.Entity.InfouDocument;
import com.gradu.infou.Domain.Entity.InfouProcessDocument;
import com.gradu.infou.Service.InFouFacadeService;
import com.gradu.infou.Service.InfouService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/infou")
@Tag(name = "3. INfoU", description = "infou 관련 api입니다.")
public class InfouController {
    private final InfouService infouService;
    private final InFouFacadeService inFouFacadeService;

    @Operation(
            summary = "강의평 작성",
            description = "강의평 작성하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @PostMapping
    public BaseResponse InfouAdd(HttpServletRequest httpServletRequest, @RequestBody AddInfouReqDto addInfouReqDto){
        inFouFacadeService.addFacadeInfou(httpServletRequest, addInfouReqDto);

        return new BaseResponse();
    }

    @Operation(
            summary = "인기 교양 목록 조회(수정 필요)",
            description = "인기 교양을 목록을 조회하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="pageable", description = "페이징 관련 정보를 입력합니다. (sort: 비워두면 score 기준으로 내림차순 정렬됩니다.)", required = true)
            }
    )
    @GetMapping("/popular")
    public BaseResponse<Page<InfouDocument>> popularGEDetailList(@PageableDefault(sort="score", direction = Sort.Direction.DESC) Pageable pageable){
        Page<InfouDocument> infouDocuments = infouService.popularGE(pageable);

        return new BaseResponse(infouDocuments);
    }

    @Operation(
            summary = "인기 교양 상세 목록 조회(미완성)",
            description = "인기 교양을 상세 목록을 조회하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @GetMapping("popular/detail")
    public BaseResponse popularGEList(){
        return new BaseResponse();
    }

    @Operation(
            summary = "추천 강의평",
            description = "나와 비슷한 사용자가 많이 조회한 강의 목록 api입니다. \n\n 로그 기록에 따라 사용자와 같은 학년, 같은 학과의 사용자들이 본 강의평을 보여줍니다. \n\n log는 강의평 상세 조회 api에서 기록됩니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="pageable", description = "sort: {count,asc}/{count,desc} \n\n ex) \"sort\": [\"count,asc\"]", required = true)
            }
    )
    @GetMapping("/recommend")
    public BaseResponse RecommendInfouList(HttpServletRequest httpServletRequest, @PageableDefault(sort="count", direction = Sort.Direction.DESC) Pageable pageable) throws IOException {
        Page<InfouProcessDocument> infouProcessDocuments = infouService.recommendInfouList(httpServletRequest, pageable);
        return new BaseResponse(infouProcessDocuments);
    }

    @Operation(
            summary = "최근 강의평",
            description = "최근 강의평 목록 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="pageable", description = "페이징 관련 정보를 입력합니다. \n\n sort: {timestamp,asc}/{timestamp,desc} \n\n ex) \"sort\": [\"timestamp,asc\"]\", default: {timestamp,desc})", required = true)
            }
    )
    @GetMapping("/recent")
    public BaseResponse<Page<InfouDocument>> RecentInfouList(@PageableDefault(sort="timestamp", direction = Sort.Direction.DESC) Pageable pageable){
        Page<InfouDocument> infouDocuments = infouService.recentInfou(pageable);
        return new BaseResponse(infouDocuments);
    }

    @Operation(
            summary = "강의평 상세 조회",
            description = "강의평 상세 조회 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="academicNumber", description = "학수번호를 입력해주세요.", required = true),
                    @Parameter(name="professorName", description = "교수명 입력합니다.", required = true),
                    @Parameter(name="pageable", description = "페이징 관련 정보를 입력합니다. \n\n size: infouDocuments의 개수를 설정합니다. \n\n sort: 정렬하고 싶은 속성, 정렬 기준(ex, {score,desc},{skill,asc},{level,desc}, \n\n score: 추천 점수, skill: 강의력, level: 난이도", required = true)
            }
    )
    @GetMapping("/details")
    public BaseResponse<InfouDetailResDto> InfouDetails(HttpServletRequest request, @PathParam("academic_number") String academicNumber, @PathParam("professor_name") String professorName, Pageable pageable) throws IOException {
        InfouDetailResDto infouDocuments = infouService.detailInfou(request, academicNumber, professorName, pageable);
        return new BaseResponse(infouDocuments);
    }

    @Operation(
            summary = "infou 강의평 검색",
            description = "infou 강의평 검색 api입니다. \n\n 학수번호와 교수님 기준으로 그룹화된 강의평 목록이 보여집니다. \n\n",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="keyword", description = "검색어를 입력해주세요.", required = true),
                    @Parameter(name="condition", description = "검색할 것을 입력합니다. \n\n (학과 검색: depart, 교수 검색: professor, 강의명 검색: lecture)", required = true),
                    @Parameter(name="pageable", description = "pageable의 sort: {average_value,asc}/{average_value,desc} \n\n ex) \"sort\": [\"average_value,asc\"]", required = true)
            }
    )
    @GetMapping("/search")
    public BaseResponse<Page<InfouProcessDocument>> InfouSearch(@PathParam("keyword") String keyword, @PathParam("condition") Kind condition, @PageableDefault(sort="average_value", direction = Sort.Direction.DESC) Pageable pageable) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Page<InfouProcessDocument> infouDocuments = infouService.searchInfou(keyword, condition, pageable);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println("infou 검색 코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());

        return new BaseResponse<>(infouDocuments);
    }

}
