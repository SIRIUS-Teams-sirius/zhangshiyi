package com.example.federatedlearning.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {  //方法
    @RequestMapping("/hello")
    public String hello() {
        return "hello Spring Boot!";
    }
}
