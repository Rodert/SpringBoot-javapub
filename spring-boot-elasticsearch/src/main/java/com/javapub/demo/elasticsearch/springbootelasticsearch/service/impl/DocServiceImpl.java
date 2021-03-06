package com.javapub.demo.elasticsearch.springbootelasticsearch.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.javapub.demo.elasticsearch.springbootelasticsearch.model.vo.News;
import com.javapub.demo.elasticsearch.springbootelasticsearch.service.DocService;
import org.apache.commons.codec.digest.DigestUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class DocServiceImpl implements DocService {

    private static final Logger logger = LoggerFactory.getLogger(DocServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    @Override
    public Boolean batchInsert(String indexName, JSONArray executedDataJsonArray) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < executedDataJsonArray.size(); i++) {
            JSONObject jsonObject = executedDataJsonArray.getJSONObject(i);
            bulkRequest.add(new IndexRequest(indexName).id(getId(jsonObject)).source(jsonObject.toJSONString(), XContentType.JSON));
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        logger.info("{}", bulkResponse);
        return true;
    }

    private String getId(JSONObject jsonObject) {
        News news = JSONObject.parseObject(jsonObject.toJSONString(), News.class);
        String id = news.getUrl();
        if (id == null || id.equals("")) {
            id = String.valueOf(UUID.randomUUID());
        }
        return DigestUtils.md5Hex(id);
    }
}
