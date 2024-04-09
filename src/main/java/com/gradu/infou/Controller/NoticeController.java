package com.gradu.infou.Controller;

import com.gradu.infou.Aop.Annotation.ExeTimer;
import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.AddInfouReqDto;
import com.gradu.infou.Domain.Dto.Controller.NoticeBookMarkReqDto;
import com.gradu.infou.Domain.Dto.Controller.NoticeListReqDto;
import com.gradu.infou.Domain.Dto.Service.NoticeListResDto;
import com.gradu.infou.Domain.Entity.NoticeBookmarkDocument;
import com.gradu.infou.Domain.Entity.NoticeDocument;
import com.gradu.infou.Repository.NoticeBookmarkRepository;
import com.gradu.infou.Service.LogService;
import com.gradu.infou.Service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
@Tag(name = "4. Notice", description = "notice 관련 api입니다.")
public class NoticeController {
    private final NoticeService noticeService;
    private final LogService logService;

    @Operation(
            summary = "모든 공지사항 조회",
            description = "모든 공지사항을 조회하는 api입니다. type이 null, tag가 값이 있는 경우는 안됩니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="type", description = "nullable, 공지사항 유형입니다. ex) 인하대학교, 컴퓨터공학과", required = false),
                    @Parameter(name="tag", description = "nullable, tag입니다. ex) 장학, 신청", required = false),
                    @Parameter(name="pageable", description = "페이징 관련 정보를 입력합니다. (sort 예시: {date,desc}, {date,asc})", required = true)
            }
    )
    @ExeTimer
    @GetMapping
    public BaseResponse NoticeList(HttpServletRequest request, @RequestParam(required = false) String type, @RequestParam(required = false) String tag, Pageable pageable){

        if((type==null||type.isBlank())&&!(tag==null||tag.isBlank()))
            throw new BaseException(BaseResponseStatus.NOT_TYPE_EXIST_TAG);

        NoticeListResDto noticeListResDto = noticeService.noticeDocumentsList(request, type, tag ,pageable);
        return new BaseResponse(noticeListResDto);
    }

    @Operation(
            summary = "즐겨찾기 공지사항 조회",
            description = "즐겨찾기한 공지사항을 조회하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="tag", description = "nullable, tag입니다. ex) 장학, 신청", required = false),
                    @Parameter(name="pageable", description = "페이징 관련 정보를 입력합니다. sort: {time,desc}/{time,asc}/{notice_date,asc)/{notice_date,desc})", required = true)
            }
    )
    @ExeTimer
    @GetMapping("/bookmark")
    public BaseResponse<NoticeBookmarkDocument> NoticeBookMarkList(HttpServletRequest request, @RequestParam(required = false) String tag, @PageableDefault(sort="date", direction = Sort.Direction.DESC) Pageable pageable){
        Page<NoticeBookmarkDocument> res = noticeService.BookmarkList(request, tag, pageable);
        log.info(String.valueOf(res.getSize()));
        return new BaseResponse(res);
    }

    @Operation(
            summary = "즐겨찾기 등록하기",
            description = "즐겨찾기한 공지사항을 조회하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @ExeTimer
    @PostMapping("/bookmark")
    public BaseResponse NoticeBookMarkAdd(HttpServletRequest request, @RequestBody NoticeBookMarkReqDto reqDto){
        noticeService.addBookmak(request, reqDto.getNoticeId());
        return new BaseResponse();
    }

    @Operation(
            summary = "공지사항 제목 검색",
            description = "공지사항 목록에서 검색하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="keyword", description = "검색어입니다(제목 검색)", required = true),
                    @Parameter(name="type", description = "학과 ex) 인하대학교, 컴퓨터공학과 등", required = true),
                    @Parameter(name="pageable", description = "페이징 관련 정보를 입력합니다. sort: {date,desc}/{date,asc}", required = true)
            }
    )
    @ExeTimer
    @GetMapping("/search")
    public BaseResponse NoticeSearch(@RequestParam String keyword, @RequestParam String type, Pageable pageable){
        Page<NoticeDocument> res = noticeService.searchNotice(keyword, type, pageable);
        return new BaseResponse(res);
    }

    @Operation(
            summary = "즐겨찾기 공지사항 제목 검색",
            description = "즐겨찾기한 공지사항을 조회하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="keyword", description = "검색어입니다(제목 검색)", required = true),
                    @Parameter(name="pageable", description = "페이징 관련 정보를 입력합니다. sort: {time,desc}/{time,asc}/{notice_date,asc)/{notice_date,desc}", required = true)
            }
    )
    @ExeTimer
    @GetMapping("/search/bookmark")
    public BaseResponse NoticeBookMarkSearch(HttpServletRequest request, String keyword, Pageable pageable){
        Page<NoticeBookmarkDocument> res = noticeService.searchNoticeBookmark(request, keyword, pageable);
        return new BaseResponse(res);
    }


    @Operation(
            summary = "공지사항 log",
            description = "공지사항을 조회할 때 log를 기록하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @ExeTimer
    @PostMapping("/log")
    public BaseResponse NoticeBookMarkList(HttpServletRequest request, @RequestBody NoticeBookMarkReqDto bookMarkReqDto){
        logService.addLog(request,"notice",bookMarkReqDto.getNoticeId());
        return new BaseResponse();
    }

    @Operation(
            summary = "추천 공지사항 목록 조회",
            description = "사용자와 비슷한 공지사항을 조회하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="pageable", description = "페이징 관련 정보를 입력합니다. sort: {date,desc}/{date,asc}", required = true)
            }
    )
    @ExeTimer
    @GetMapping("/recommend")
    public BaseResponse NoticeRecommendList(HttpServletRequest httpServletRequest, Pageable pageable) throws IOException {
        Page<NoticeDocument> result = noticeService.recommendNoticeList(httpServletRequest, pageable);
        return new BaseResponse(result);
    }

//    @Operation(
//            summary = "즐겨찾기 공지사항 조회",
//            description = "즐겨찾기한 공지사항을 조회하는 api입니다.",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
//                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
//                    })
//            }
//    )
//    @GetMapping("/c")
//    public BaseResponse NoticeBookMarkList(){
//        return new BaseResponse();
//    }


}
