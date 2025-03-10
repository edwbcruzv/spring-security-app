package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello word Springboot by Edwin cruz";
    }

    @GetMapping("/hello-secure")
    public String helloSecure(){
        return "Hello word secure by Edwin cruz";
    }


}
