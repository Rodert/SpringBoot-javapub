package com.javapub.demo.elasticsearch.springbootelasticsearch.controller;

import com.javapub.demo.elasticsearch.springbootelasticsearch.service.SearchService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/4/5 20:56
 * @Version: 1.0
 * @Description:
 */
@RestController
@ResponseBody
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("news")
    public String searchNews(String keyword, String indexName) throws IOException {
        return searchService.search(keyword, indexName);
    }

}
