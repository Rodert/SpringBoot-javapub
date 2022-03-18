package com.javapub.demo.elasticsearch.springbootelasticsearch.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping("search")
public class SearchController {

    @RequestMapping("news")
    public String searchNews(String keyword) {

        return "";
    }

}
