package com.javapub.spring.boot.security.demo.springbootsecuritydemo.config2;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 配置自定义登录页
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin") // admin角色用户可以访问/admin/...路径下的接口资源
                .antMatchers("/user/**").hasRole("user") // user角色用户可以访问/user/...路径下的接口资源
                .anyRequest().authenticated().and() // and 方法表示结束当前标签，上下文回到HttpSecurity，开启新一轮的配置
                .formLogin().loginPage("/login.html") // 自定义登录页路径
//                .loginProcessingUrl("/doLogin") // 自定义登录接口。不设置的话，Spring Security也会自动帮我们注册一个 /login.html 的POST 接口，用来处理登录逻辑的，因此登录页form表单提交action属性需要为 /login.html。
                .defaultSuccessUrl("/index", true)
//                .successForwardUrl("/index")
//                .and().logout().logoutUrl("/logout") //登出url
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST")) //登出
//                .deleteCookies()//删除cookies
//                .clearAuthentication(true)//清除认证
//                .invalidateHttpSession(true) //清除session
                .permitAll() // 登录相关的页面/接口不要被拦截。
                .and().csrf().disable();
    }

    /**
     * 在SpringBoot中的SpringSecurity的配置类中，http.permitAll()与web.ignoring()的区别
     * <p></p>
     * 虽然这两个都是继承WebSecurityConfigurerAdapter后重写的方法，但是http.permitAll不会绕开springsecurity的过滤器验证，相当于只是允许该路径通过过滤器，而web.ignoring是直接绕开spring security的所有filter，直接跳过验证。
     */

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 配置放行的 URL 地址
        web.ignoring().antMatchers("/css/**", "/js/**", "/fonts/**");
    }

    /**
     * 用户配置方法1
     *
     * @param auth
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 可以配置多个用户
//        auth.inMemoryAuthentication() // 方便测试数据存于内存中
//                .withUser("jsh").password(new BCryptPasswordEncoder().encode("111111")).roles("admin") //用户jsh
//                .and().withUser("jsh2").password(new BCryptPasswordEncoder().encode("222222")).roles("admin");
//    }

    /**
     * 用户配置方法2
     *
     * @return
     */
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("jsh1").password(new BCryptPasswordEncoder().encode("111111")).roles("admin").build());
        manager.createUser(User.withUsername("jsh2").password(new BCryptPasswordEncoder().encode("222222")).roles("user").build());
        System.out.println(new BCryptPasswordEncoder().encode("111111"));
        return manager;
    }


    // 密码加密，官方推荐BCryptPasswordEncoder加密
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
