package com.javapub.demo.elasticsearch.springbootelasticsearch.config;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ElasticsearchClientConfiguration {

    @Value("${elasticsearch.hosts}")
    private String[] hosts;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    @Value("${elasticsearch.schema}")
    private String schema;


    @Bean
    public RestHighLevelClient restHighLevelClient() {
        List<HttpHost> httpHostList = getHttpHostList();
        HttpHost[] httpHosts = new HttpHost[httpHostList.size()];
        RestClientBuilder builder = RestClient.builder(httpHostList.toArray(httpHosts));
        //username+passwd
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
            builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
        }
        System.out.println("构建完成,配置:" + builder);
        return new RestHighLevelClient(builder);
    }

    /**
     * 为了支持多种数据Yml配置
     * 1. ip:port
     * 2. ip port
     * 3. http://ip:port
     *
     * @return
     */
    public List<HttpHost> getHttpHostList() {
        return Arrays.stream(hosts).map(host -> {
            String[] split = host.split(":");
            if (split.length == 2) {
                if (StringUtils.isEmpty(schema)) {
                    return new HttpHost(split[0], Integer.parseInt(split[1]));
                } else {
                    return new HttpHost(split[0], Integer.parseInt(split[1]), schema);
                }
            }
            if (StringUtils.isEmpty(schema)) {
                return new HttpHost(split[0], port);
            } else {
                return new HttpHost(split[0], port, schema);
            }
        }).collect(Collectors.toList());
    }
}
