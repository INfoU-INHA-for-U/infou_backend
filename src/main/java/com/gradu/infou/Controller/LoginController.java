package com.gradu.infou.Controller;

import com.gradu.infou.Service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login/oauth2", produces="application/json")
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/code/{registrationId}")
    public void googleLogin(@RequestParam("code") String code, @PathVariable String registrationId){
        System.out.println("111111");
        loginService.socialLogin(code, registrationId);
    }
}
