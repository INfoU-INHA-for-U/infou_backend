package com.gradu.infou.Controller;

import com.gradu.infou.Config.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @GetMapping
    private BaseResponse<String> test(){
        return new BaseResponse("user");
    }
}
