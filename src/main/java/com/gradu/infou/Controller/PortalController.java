package com.gradu.infou.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortalController {
    @GetMapping("/search")
    public String search(){
        return "search";
    }
}
