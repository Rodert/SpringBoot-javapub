package com.javapub.spring.boot.security.demo.springbootsecuritydemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @RequestMapping("/admin/hello")
    public String adminHello() {
        return "hello admin";
    }

    @RequestMapping("/user/hello")
    public String userHello() {
        return "hello user";
    }
}

