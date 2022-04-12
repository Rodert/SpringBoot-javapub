package com.javapub.demo.trace.springboottrace.global;

import com.javapub.demo.trace.springboottrace.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class GlobalInterceptor extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**").excludePathPatterns("");
    }

}
