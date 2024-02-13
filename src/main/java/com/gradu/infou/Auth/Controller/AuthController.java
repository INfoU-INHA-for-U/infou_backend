package com.gradu.infou.Auth.Controller;

import com.gradu.infou.Auth.Dto.JoinReqDto;
import com.gradu.infou.Auth.Dto.JoinResDto;
import com.gradu.infou.Auth.Dto.LoginReqDto;
import com.gradu.infou.Auth.Dto.LoginResDto;
import com.gradu.infou.Auth.Service.AuthService;
import com.gradu.infou.Config.BaseResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/join")
    private BaseResponse<JoinResDto> join(@RequestBody JoinReqDto joinReqDto){

        JoinResDto resDto = authService.join(joinReqDto);

        return new BaseResponse<>(resDto);
    }

    @PostMapping("/login")
    private BaseResponse<LoginResDto> login(@RequestBody LoginReqDto loginReqDto){
        return new BaseResponse<>(authService.login(loginReqDto));
    }
}
