package com.javapub.springbootmybatis.service;

import com.javapub.springbootmybatis.entity.User;
import com.javapub.springbootmybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description
 * @author: wangshiyu
 */

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User Sel(int id) {
        return userMapper.Sel(id);
    }

    public long updateBatch(List<User> users) {
        return userMapper.updateBatch(users);
    }
}
