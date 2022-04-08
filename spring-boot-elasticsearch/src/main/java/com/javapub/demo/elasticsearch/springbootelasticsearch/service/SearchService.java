package com.javapub.demo.elasticsearch.springbootelasticsearch.service;

import java.io.IOException;

public interface SearchService {

    String search(String keyword, String indexName) throws IOException;

}
