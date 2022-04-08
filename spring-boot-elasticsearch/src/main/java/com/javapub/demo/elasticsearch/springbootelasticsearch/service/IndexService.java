package com.javapub.demo.elasticsearch.springbootelasticsearch.service;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public interface IndexService {

    /**
     * 初始化构建索引
     *
     * @param indexName
     * @throws IOException
     */
    Boolean initIndex(String indexName) throws IOException;

    Boolean createIndex(String indexName) throws IOException;

    void buildMapping(String indexName, Class clazz, XContentBuilder xContentBuilder) throws IOException;

    Boolean putMapping(String indexName, XContentBuilder xContentBuilder) throws IOException;

}
