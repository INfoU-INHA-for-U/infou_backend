package com.gradu.infou.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/test")
    public String healthCheck(){
        return "health check";
    }

}
