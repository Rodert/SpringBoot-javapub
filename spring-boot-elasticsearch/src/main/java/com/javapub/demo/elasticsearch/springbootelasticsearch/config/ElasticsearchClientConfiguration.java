package com.javapub.demo.elasticsearch.springbootelasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ElasticsearchClientConfiguration {
    public static final String DEFAULT_SEPARATOR = ",";

    @Autowired
    private ElensDataElasticsearchProperties elensDataElasticsearchProperties;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String hosts = elensDataElasticsearchProperties.getHosts();
        String[] hostArray = hosts.split(DEFAULT_SEPARATOR);
        String schema = elensDataElasticsearchProperties.getSchema();
        List<HttpHost> httpHostList = Arrays.stream(hostArray).map(host -> {
            String[] split = host.split(":");
            if (split.length == 2) {
                if (StringUtils.isEmpty(schema)) {
                    return new HttpHost(split[0], Integer.parseInt(split[1]));
                }
                if (null == elensDataElasticsearchProperties.getSchema() || elensDataElasticsearchProperties.getSchema().isEmpty())
                    return new HttpHost(split[0], Integer.parseInt(split[1]));
                else new HttpHost(split[0], Integer.parseInt(split[1]), schema);
            }
            if (null == elensDataElasticsearchProperties.getSchema() || elensDataElasticsearchProperties.getSchema().isEmpty())
                return new HttpHost(split[0], Integer.parseInt(elensDataElasticsearchProperties.getPort()));
            else return new HttpHost(split[0], Integer.parseInt(elensDataElasticsearchProperties.getPort()), schema);

        }).collect(Collectors.toList());
        HttpHost[] httpHosts = new HttpHost[httpHostList.size()];
        RestClientBuilder builder = RestClient.builder(httpHostList.toArray(httpHosts));

        if (!(null == elensDataElasticsearchProperties.getUsername() || elensDataElasticsearchProperties.getUsername().isEmpty() || null == elensDataElasticsearchProperties.getPassword() || elensDataElasticsearchProperties.getPassword().isEmpty())) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elensDataElasticsearchProperties.getUsername(), elensDataElasticsearchProperties.getPassword()));
            builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
        }
        System.out.println("#####");
        return new RestHighLevelClient(builder);
    }
}
