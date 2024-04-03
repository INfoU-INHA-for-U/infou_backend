package com.gradu.infou.Controller;

import com.gradu.infou.Config.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Test-Admin", description = "admin 유저 관련 api test 입니다.")
public class AdminController {
    @GetMapping
    private BaseResponse<String> test(){
        return new BaseResponse<>("admin");
    }
}
