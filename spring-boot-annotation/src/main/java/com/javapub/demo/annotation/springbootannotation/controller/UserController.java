package com.javapub.demo.annotation.springbootannotation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/1/25 15:03
 * @Version: 1.0
 * @Description:
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/user-info")
    public String UserInfo() {
        return "this is UserController.UserInfo ";
    }

    @RequestMapping("/user-info-2")
    public String UserInfo2() {
        return "this is UserController.UserInfo2 ";
    }

}
