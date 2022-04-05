package com.javapub.demo.elasticsearch.springbootelasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.javapub.demo.elasticsearch.springbootelasticsearch.model.vo.News;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
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
@RequestMapping("doc")
@ResponseBody
public class DocController {

    @Autowired
    private RestHighLevelClient client;

    @RequestMapping("get")
    public String getDoc() {

        return "";
    }

    @RequestMapping("add")
    public String addDoc() throws IOException {
        News news = new News();
        news.setTitle("今天新闻");
        news.setSummary("今天新闻摘要");
        news.setAuthority(true);
        System.out.println(news);
        IndexRequest indexRequest = new IndexRequest("news");
        indexRequest.source(JSON.toJSONString(news), XContentType.JSON).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        RestStatus restStatus = indexResponse.status();
        System.out.println(restStatus);
        return "" + restStatus;
    }

}
