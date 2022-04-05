package com.javapub.demo.elasticsearch.springbootelasticsearch;

import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringBootElasticsearchApplicationTests {

    @Test
    void contextLoads() {
    }

    //    @Qualifier("elasticsearchRestHighLeveslClient")
    @Qualifier("restHighLevelClient")
    @Autowired
    private RestHighLevelClient client;

    public final static String indexName = "index_test_1";
    public final static String typeName = "type";
    public final static String indexAliasesName = "index_aliases_test_1";

    /**
     * 新建索引
     *
     * @throws IOException
     */
    @Test
    void create() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    /**
     * 构建mapping
     */
    @Test
    void putMapping() throws IOException {
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject();
        xContentBuilder.startObject("properties");

        xContentBuilder.startObject("name");
        xContentBuilder.field("type", "text");
//        xContentBuilder.field("index", "not_analyzed");
        xContentBuilder.endObject();

        xContentBuilder.endObject();
        xContentBuilder.endObject();
        System.out.println(xContentBuilder.getOutputStream().toString());
        System.out.println(xContentBuilder.toString());
        PutMappingRequest putMappingRequest = new PutMappingRequest(indexName).type(typeName).source(xContentBuilder);
        AcknowledgedResponse acknowledgedResponse = client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse);
    }

    /**
     * 新增数据
     *
     * @throws IOException
     */
    @Test
    void add() throws IOException {
        String id = "1";
        IndexRequest indexRequest = new IndexRequest(indexName, typeName, id);
        indexRequest.source("{\"name\":\"rodert\"}", XContentType.JSON).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        RestStatus restStatus = indexResponse.status();
        //结果
        restStatus = RestStatus.CREATED;
    }

    /**
     * 搜索查询
     *
     * @throws IOException
     */
    @Test
    void search() throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("name", "rodert"));
        System.out.println(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder.query(boolQueryBuilder));
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
    }

    /**
     * 索引别名
     * \n
     * 一般先判断索引是否存在
     *
     * TODO 别名的替换、删除、添加
     */
    @Test
    void aliasDispose() throws IOException {
        boolean indexExist = isIndexExist(client, indexName);
//        if (indexExist) {
//            GetAliasesRequest getAliasesRequest = new GetAliasesRequest();
//            getAliasesRequest.aliases(indexAliasesName);
//            GetAliasesResponse getAliasesResponse = client.indices().getAlias(getAliasesRequest, RequestOptions.DEFAULT);
//            Map<String, Set<AliasMetaData>> aliasesResponseAliases = getAliasesResponse.getAliases();
//            if (aliasesResponseAliases.isEmpty()) {
//                //    DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
//
//            } else {
//
//            }
//        } else {
            IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();
            IndicesAliasesRequest.AliasActions aliasActions = new IndicesAliasesRequest.AliasActions(
                    IndicesAliasesRequest.AliasActions.Type.ADD);
            aliasActions.index(indexName).alias(indexAliasesName);
            indicesAliasesRequest.addAliasAction(aliasActions);
            AcknowledgedResponse acknowledgedResponse = client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);
            System.out.println(acknowledgedResponse);
//        }
    }

    /**
     * 判断索引
     */
    static boolean isIndexExist(RestHighLevelClient client, String indexName) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(indexName);
        return client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }


}
