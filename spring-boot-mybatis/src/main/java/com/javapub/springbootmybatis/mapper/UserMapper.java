package com.javapub.springbootmybatis.mapper;

import com.javapub.springbootmybatis.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description
 * @author: wangshiyu
 */

@Repository
public interface UserMapper {
    User Sel(int id);

    long updateBatch(List<User> users);
}
