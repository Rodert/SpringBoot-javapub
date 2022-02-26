package com.javapub.demo.elasticsearch.springbootelasticsearch.demo;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;

import java.util.ArrayList;

/**
 * 在elasticsearch入门阶段，我们总能看到关于mysql和es对比。
 * \n
 * 类比mysql，我们需要=、>、>=、<、<= 、or、and、in、like、count、sum、group by、order by、limit
 * \n
 *
 * @see https://blog.csdn.net/hao134838/article/details/80994403
 */
public class MyQueryBuilder {
    /**
     * //lte   <=
     * //lt    <
     * //gte   >=
     * //gt    >
     */
    void myRange() {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders
                .rangeQuery("updateTime")
                .lte(System.currentTimeMillis());

        QueryBuilders.termQuery("name", new ArrayList<>());
    }

    /**
     * = 等于
     */
    void term() {
        QueryBuilders.termQuery("name", "JavaPub");
    }

    /**
     * and
     * or
     */
    void andOr() {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // and
        boolQueryBuilder.must(QueryBuilders.termQuery("name", "JavaPub"));

        // or
        boolQueryBuilder.should(QueryBuilders.termQuery("platform", "微信公众号"));
    }

    /**
     * in
     */
    void in() {
        QueryBuilders.termsQuery("names", new ArrayList<>());
    }

    /**
     * like
     */
    void like() {
        String query = "JavaPub";
        // 正则匹配
        QueryBuilders.wildcardQuery("name", "*" + query + "*");
        // 短语匹配
        QueryBuilders.matchPhraseQuery("content", query);
    }

    /**
     * sum & count
     */
    void sumCount() {
//        AggregationBuilder debtTotalSum = AggregationBuilders.sum(DEBT_TOTAL_SUM_KEY).field("debtTotal");
//        AggregationBuilder debtTotalCount = AggregationBuilders.count(DEBT_TOTAL_COUNT_KEY).field("debtTotal");
    }

    /**
     * order by & limit
     */
    void orderByLimit() {
//        SearchResponse searchResponse = client.prepareSearch(ElasticSearchUtil.getIndexName())
//                .setTypes(ElasticSearchUtil.TYPE_NAME)
//                .setQuery(boolQueryBuilder)
//                //指定查询字段
//                .addStoredField("id")
//                .addSort(order, sortOrder)
//                //分页
//                .setFrom((currentPage - 1) * limit).setSize(limit)
//                .execute()
//                .actionGet();
    }

    /**
     * batch
     */
    void batch() {
//        new BulkRequestBuilder()
    }


}
