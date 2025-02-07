package com.pollsystem.simpleproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/sessionInfo")
    public String getSessionInfo(HttpServletRequest request){
        System.out.println(request.getSession().getId());
        return request.getSession().getId();
    }
}
