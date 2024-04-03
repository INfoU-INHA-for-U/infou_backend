package com.gradu.infou.Controller;

import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Domain.Dto.Controller.UserPutReqDto;
import com.gradu.infou.Domain.Dto.Service.UserDetailResDto;
import com.gradu.infou.Domain.Entity.RewardDocument;
import com.gradu.infou.Domain.Entity.User;
import com.gradu.infou.Repository.UserRepository;
import com.gradu.infou.Service.RewardService;
import com.gradu.infou.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "User 관련 api입니다. (로그인 후 사용)")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RewardService rewardService;

    @Operation(
            summary = "마이페이지 조회",
            description = "마이페이지를 조회하는 api입니다. \n\n",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @GetMapping("/detail")
    private BaseResponse userDetail(HttpServletRequest request){
        UserDetailResDto userDetailResDto = userService.detailUser(request);
        return new BaseResponse(userDetailResDto);
    }


    @Operation(
            summary = "유저 수정",
            description = "사용자 정보를 수정하는 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @PutMapping
    private BaseResponse<String> userUpdate(HttpServletRequest request, @RequestBody UserPutReqDto userPutReqDto){
        userService.userUpdate(request, userPutReqDto);

        return new BaseResponse();
    }



    @Operation(
            summary = "닉네임 불러오기",
            description = "닉네임을 불러오는 api입니다. \n\n",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @GetMapping("/nick")
    private BaseResponse<String> nickDuplicate(HttpServletRequest request){
        String nick=userService.getNick(request);
        return new BaseResponse(nick);
    }

    @Operation(
            summary = "유저 포인트 내역 불러오기",
            description = "유저 포인트 내역을 불러오는 api입니다. \n\n",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="pageable", description = "pageable의 sort: {date,asc}/{date,desc}", required = true)
            }
    )
    @GetMapping("/reward")
    private BaseResponse userRewardList(HttpServletRequest request, Pageable pageable){
        Page<RewardDocument> rewardDocuments = rewardService.rewardList(request, pageable);
        return new BaseResponse(rewardDocuments);
    }

    //    @Operation(
//            summary = "마이페이지 조회",
//            description = "마이페이지를 조회하는 api입니다. \n\n" +
//                    "keyword: 검색어 \n\n"+
//                    "pageable의 sort: {option_1,asc}/{option_2,desc}/{option_3,asc}/{option_4,asc}/{option_5,asc} \n\n {option_1,asc}: option_1을 기준으로 오름차순 정렬합니다. \n\n"+
//                    "ex) \"sort\": [\"option_1,asc\"]"
//            ,
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
//                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
//                    })
//            }
//    )
//    @GetMapping
//    private BaseResponse<String> test(HttpServletRequest request){
//        //rewardService.increaseReward(request);
//
//        return new BaseResponse("user");
//    }


}
