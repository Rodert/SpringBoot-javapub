package com.javapub.spring.boot.security.demo.springbootsecuritydemo.config;

/**
 * 配置类添加用户名密码
 */


//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 可以配置多个用户
//        auth.inMemoryAuthentication()  // 方便测试数据存于内存中
//                .withUser("jsh") //用户名
//                .password(new BCryptPasswordEncoder().encode("111111")) //密码，加个密
//                .roles("admin") //角色
//                .and()
//                .withUser("jsh2")
//                .password(new BCryptPasswordEncoder().encode("222222"))
//                .roles("admin");
//    }
//
//    // 密码加密，官方推荐BCryptPasswordEncoder加密
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
