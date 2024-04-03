package com.gradu.infou.Auth.Controller;

import com.gradu.infou.Auth.Dto.*;
import com.gradu.infou.Auth.Service.AuthService;
import com.gradu.infou.Config.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Auth", description = "로그인/회원가입 관련 api입니다. \n logout을 할 때는 /api/v1/auth/logout을 하면 됩니다.")
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "회원가입",
            description = "회원가입 api입니다. 회원 정보를 입력합니다. authId: 구글 로그인하고 얻는 id 정보, email: '@inha.edu' 또는 '@inha.ac.kr'이어야 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @PostMapping("/join")
    private BaseResponse<TokenResDto> join(@RequestBody JoinReqDto joinReqDto){
        return new BaseResponse<>(authService.join(joinReqDto));
    }

    @Operation(
            summary = "로그인",
            description = "로그인 api입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @PostMapping("/login")
    private BaseResponse<TokenResDto> login(@RequestBody LoginReqDto loginReqDto){
        return new BaseResponse<>(authService.login(loginReqDto));
    }

    @Operation(
            summary = "Refresh token 갱신 api",
            description = "Refresh token 갱신하는 api입니다. \n access token이 만료됐을 경우, 다시 발급받을 수 있습니다. \n 헤더에 refresh token을 넣어줍니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            }
    )
    @PostMapping("/refresh-token")
    private BaseResponse<TokenResDto> refreshToken(HttpServletRequest req, HttpServletResponse res){
        return new BaseResponse<>(authService.refreshToken(req, res));
    }

    @Operation(
            summary = "닉네임 중복 검사",
            description = "닉네임을 중복 검사하는 api입니다. \n\n",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                    })
            },
            parameters = {
                    @Parameter(name="nick", description = "중복 검사할 닉네임을 적어주세요", required = true)
            }
    )
    @GetMapping("")
    private BaseResponse<String> nickDuplicate(@RequestParam("nick") String nick){
        authService.duplicateNick(nick);
        return new BaseResponse();
    }

}
