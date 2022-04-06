package com.javapub.demo.elasticsearch.springbootelasticsearch.controller;

import com.javapub.demo.elasticsearch.springbootelasticsearch.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("internal")
public class InitController {

    @Autowired
    private IndexService indexService;

    /**
     * 构建测试数据
     */
    @RequestMapping("init")
    @ResponseBody
    public String init(@RequestParam("type") String type) {
        if (!type.equals("news")) {
            return "非预设 news";
        }
        String indexName = "news-index";
        new Thread(() -> {
            try {
                indexService.initIndex(indexName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        return "";
    }

}
