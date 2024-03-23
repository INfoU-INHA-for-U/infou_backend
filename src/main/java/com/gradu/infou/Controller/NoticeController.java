package com.gradu.infou.Controller;

import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.AddInfouReqDto;
import com.gradu.infou.Domain.Dto.Controller.NoticeListReqDto;
import com.gradu.infou.Domain.Dto.Service.NoticeListResDto;
import com.gradu.infou.Domain.Entity.NoticeBookmarkDocument;
import com.gradu.infou.Domain.Entity.NoticeDocument;
import com.gradu.infou.Repository.NoticeBookmarkRepository;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
@Tag(name = "Notice", description = "notice 관련 api입니다.")
public class NoticeController {
    private final NoticeService noticeService;
    @Operation(
            summary = "모든 공지사항 조회",
            description = "모든 공지사항을 조회하는 api입니다. type이 null, tag가 값이 있는 경우는 안됩니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="type", description = "nullable, 공지사항 유형입니다. ex) 인하대, 컴퓨터공학과", required = false),
                    @Parameter(name="tag", description = "nullable, tag입니다. ex) 장학, 신청", required = false),
                    @Parameter(name="pageable", description = "페이징 관련 정보를 입력합니다. (sort 예시: {date,desc}, {date,asc})", required = true)
            }
    )
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
            }
    )
    @GetMapping("/bookmark")
    public BaseResponse<NoticeBookmarkDocument> NoticeBookMarkList(HttpServletRequest request, @RequestParam(required = false) String tag, Pageable pageable){
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
    @PostMapping("/bookmark")
    public BaseResponse NoticeBookMarkAdd(HttpServletRequest request, String noticeId){
        noticeService.addBookmak(request, noticeId);
        return new BaseResponse();
    }

    @Operation(
            summary = "공지사항 제목 검색",
            description = "공지사항 목록에서 검색하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
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
            }
    )
    @GetMapping("/search/bookmark")
    public BaseResponse NoticeBookMarkSearch(HttpServletRequest request, String keyword, Pageable pageable){
        Page<NoticeBookmarkDocument> res = noticeService.searchNoticeBookmark(request, keyword, pageable);
        return new BaseResponse(res);
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
