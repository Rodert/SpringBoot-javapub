package cn.net.javapub;

import cn.net.javapub.entity.User;
import cn.net.javapub.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MybatisPlusSampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        userMapper.insert(user);
        assertThat(user.getId()).isNotNull();
    }
}