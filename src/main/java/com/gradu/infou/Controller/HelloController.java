package com.gradu.infou.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/login")
    public String login(){
        return "loginForm";
    }
    @GetMapping("/main")
    public String main(){
        return "hello";
    }
}
