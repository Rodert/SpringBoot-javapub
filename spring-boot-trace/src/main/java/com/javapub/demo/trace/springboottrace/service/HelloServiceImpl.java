package com.javapub.demo.trace.springboottrace.service;

import com.javapub.demo.trace.springboottrace.utils.RestTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Value("${server.port}")
    private String port;

    @Override
    public String test() {
        String url = "http://127.0.0.1:" + port + "/hello/test";
        logger.info("{}", url);
        String s = RestTemplateUtil.doGet(url);
        logger.info("{}", s);
        return s;
    }

}
