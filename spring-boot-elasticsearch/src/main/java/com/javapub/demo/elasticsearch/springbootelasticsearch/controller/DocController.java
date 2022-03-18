package com.javapub.demo.elasticsearch.springbootelasticsearch.controller;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("doc")
@ResponseBody
public class DocController {

    @Autowired
    private RestHighLevelClient client;

    @RequestMapping("get")
    public String getDoc() {

        return "";
    }

}
