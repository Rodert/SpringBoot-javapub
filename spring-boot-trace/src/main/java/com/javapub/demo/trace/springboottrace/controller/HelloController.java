package com.javapub.demo.trace.springboottrace.controller;

import com.javapub.demo.trace.springboottrace.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "hello")
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HelloService helloService;

    @RequestMapping
    @ResponseBody
    String hello() {
        logger.info("request {}", this.getClass().getName());
        return helloService.test();
    }

    @RequestMapping(value = "test")
    @ResponseBody
    String test() {
        logger.info("request {}", this.getClass().getName());
        return "123";
    }

}
