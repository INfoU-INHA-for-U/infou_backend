package com.gradu.infou.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portal")
public class PortalController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
