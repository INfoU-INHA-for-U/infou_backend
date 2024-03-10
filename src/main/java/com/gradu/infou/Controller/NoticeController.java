package com.gradu.infou.Controller;

import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Domain.Dto.Controller.AddInfouReqDto;
import com.gradu.infou.Service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
public class NoticeController {
    private final NoticeService noticeService;
    @Operation(
            summary = "모든 공지사항 조회",
            description = "모든 공지사항을 조회하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @GetMapping
    public BaseResponse NoticeList(){
        return new BaseResponse();
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
    @GetMapping
    public BaseResponse NoticeBookMarkList(){
        return new BaseResponse();
    }


}
