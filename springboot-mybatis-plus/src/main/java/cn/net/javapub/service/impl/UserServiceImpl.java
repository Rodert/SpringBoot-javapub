package cn.net.javapub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.net.javapub.entity.User;
import cn.net.javapub.service.UserService;
import cn.net.javapub.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author shiyuwang
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-06-28 13:47:16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

}




