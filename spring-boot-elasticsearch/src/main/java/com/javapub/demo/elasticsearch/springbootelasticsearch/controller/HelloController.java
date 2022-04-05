package com.javapub.demo.elasticsearch.springbootelasticsearch.controller;


import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/4/5 21:33
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private RestHighLevelClient client;

    @RequestMapping("hello")
    String hello() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("");
        GetIndexResponse getIndexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);

        return "hello! indices: " + getIndexResponse.getIndices();
    }

    @RequestMapping("hello2")
    String hello2() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("*");
        GetIndexResponse getIndexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);

        return "hello! indices: " + getIndexResponse.getIndices();
    }
}
