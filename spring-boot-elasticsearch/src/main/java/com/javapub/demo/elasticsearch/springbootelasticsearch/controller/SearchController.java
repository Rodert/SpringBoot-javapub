package com.javapub.demo.elasticsearch.springbootelasticsearch.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("news")
    public String searchNews(String keyword) {

        return "";
    }

}
