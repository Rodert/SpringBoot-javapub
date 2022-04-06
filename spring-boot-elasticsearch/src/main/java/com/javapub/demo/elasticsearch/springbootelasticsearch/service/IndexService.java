package com.javapub.demo.elasticsearch.springbootelasticsearch.service;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public interface IndexService {

    void initIndex(String indexName) throws IOException;

    void createIndex(String indexName) throws IOException;

    void buildMapping(String indexName, Class clazz, XContentBuilder xContentBuilder) throws IOException;

    void putMapping(String indexName, XContentBuilder xContentBuilder) throws IOException;

}
