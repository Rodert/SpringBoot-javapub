package com.javapub.demo.trace.springboottrace.utils;

import com.javapub.demo.trace.springboottrace.interceptor.RestTemplateTraceIdInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestTemplateUtil {

    public RestTemplateUtil() {
    }

    public static String doGet(String url) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Arrays.asList(new RestTemplateTraceIdInterceptor()));
        return restTemplate.getForObject(url, String.class);
    }

}
