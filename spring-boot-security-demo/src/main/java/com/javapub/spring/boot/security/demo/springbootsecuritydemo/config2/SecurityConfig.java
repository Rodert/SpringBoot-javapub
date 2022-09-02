package com.javapub.spring.boot.security.demo.springbootsecuritydemo.config2;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 配置自定义登录页
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and() // and 方法表示结束当前标签，上下文回到HttpSecurity，开启新一轮的配置
                .formLogin()
                .loginPage("/login.html") // 自定义登录页路径
//                .loginProcessingUrl("/doLogin") // 自定义登录接口。不设置的话，Spring Security也会自动帮我们注册一个 /login.html 的POST 接口，用来处理登录逻辑的，因此登录页form表单提交action属性需要为 /login.html。
                .permitAll() // 登录相关的页面/接口不要被拦截。
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 配置放行的 URL 地址
        web.ignoring().antMatchers("/css/**", "/js/**", "/fonts/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 可以配置多个用户
        auth.inMemoryAuthentication() // 方便测试数据存于内存中
                .withUser("jsh")
                .password(new BCryptPasswordEncoder().encode("111111"))
                .roles("admin")
                .and()
                .withUser("jsh2")
                .password(new BCryptPasswordEncoder().encode("222222"))
                .roles("admin");
    }

    // 密码加密，官方推荐BCryptPasswordEncoder加密
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
