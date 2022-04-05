package com.javapub.demo.elasticsearch.springbootelasticsearch.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("index")
@RestController
@ResponseBody
public class IndexController {

    private static final Log logger = LogFactory.getLog(IndexController.class);

    @Autowired
    private RestHighLevelClient client;

    /**
     * @param indexName 索引名
     * @param aliasName 别名
     * @param shards    分片
     * @param replicas  副本
     * @return
     */
    @RequestMapping("create")
    public Boolean createIndex(String indexName, @RequestParam(name = "aliasName", required = false) String aliasName, @RequestParam(name = "shards", required = false, defaultValue = "5") Integer shards, @RequestParam(name = "replicas", required = false, defaultValue = "1") Integer replicas) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        Settings.Builder builder = Settings.builder().put("index.mapper.dynamic", false).put("index.number_of_shards", shards == null ? 5 : shards).put("index.number_of_replicas", replicas == null ? 1 : replicas).put("index.max_result_window", 1000000);
        request.settings(builder);
        if (StringUtils.isNotEmpty(aliasName)) {
            request.alias(new Alias(aliasName));
        }
        request.timeout(TimeValue.timeValueMinutes(1));
        request.masterNodeTimeout(TimeValue.timeValueMinutes(2));
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        //索引创建成功
        boolean acknowledged = createIndexResponse.isAcknowledged();
        //副本创建成功
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        if (acknowledged && shardsAcknowledged) {
            logger.info("索引创建成功");
        }
        return acknowledged && shardsAcknowledged;
    }

}
