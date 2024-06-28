package cn.net.javapub;

import cn.net.javapub.entity.User;
import cn.net.javapub.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("cn.net.javapub.mapper")
class MybatisPlusBaseMapperTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询数据
     */
    @Test
    public void testSelectUser() {
        System.out.println(("----- 开始测试 mybatis-plus 查询数据 ------"));
        //  selectList() 方法的参数为 mybatis-plus 内置的条件封装器 Wrapper，这里不填写表示无任何条件，全量查询
        List<User> userList = userMapper.selectList(null);

        userList.forEach(System.out::println);
    }


}