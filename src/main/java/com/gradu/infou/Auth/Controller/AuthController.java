package com.gradu.infou.Auth.Controller;

import com.gradu.infou.Auth.Dto.*;
import com.gradu.infou.Auth.Service.AuthService;
import com.gradu.infou.Config.BaseResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/join")
    private BaseResponse<TokenResDto> join(@RequestBody JoinReqDto joinReqDto){
        return new BaseResponse<>(authService.join(joinReqDto));
    }

    @PostMapping("/login")
    private BaseResponse<TokenResDto> login(@RequestBody LoginReqDto loginReqDto){
        return new BaseResponse<>(authService.login(loginReqDto));
    }

    @PostMapping("/refresh-token")
    private BaseResponse<TokenResDto> refreshToken(HttpServletRequest req, HttpServletResponse res){
        return new BaseResponse<>(authService.refreshToken(req, res));
    }

}
