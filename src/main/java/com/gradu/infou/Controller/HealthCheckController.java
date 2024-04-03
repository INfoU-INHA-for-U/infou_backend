package com.gradu.infou.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Test-Health", description = "health check 관련 test api입니다.")
public class HealthCheckController {

    @GetMapping("/test")
    public String healthCheck(){
        return "health check";
    }

}
