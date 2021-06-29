package com.javapub.springbootmybatis.controller;

import com.javapub.springbootmybatis.entity.User;
import com.javapub.springbootmybatis.entity.UserInfo;
import com.javapub.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author: wangshiyu
 */

@RestController
@RequestMapping("/test")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/test1")
    public int test1(int id) {
        System.out.println(id);
        return id;
    }

    @RequestMapping("/test")
    public Integer test(@RequestBody User user, UserInfo userInfo) {
        System.out.println(user.toString());
        System.out.println(userInfo.toString());
        return user.getQq();
    }

    @RequestMapping(value = "/Sel")
    public User Sel(int id) {
        return userService.Sel(id);
    }

    @RequestMapping(value = "/testUpdate")
    public long updateBatch() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            User user = new User();
            user.setUserId(i);
            user.setUserStatus(i * 10);
            users.add(user);
        }
        return userService.updateBatch(users);
    }

}
