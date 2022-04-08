package com.javapub.demo.elasticsearch.springbootelasticsearch.service;

import com.alibaba.fastjson.JSONArray;

import java.io.IOException;

public interface DocService {

    Boolean batchInsert(String indexName, JSONArray executedDataJsonArray) throws IOException;

}
