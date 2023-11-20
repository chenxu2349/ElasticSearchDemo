package com.example.elasticsearchdemo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ElasticSearchConfig
 * @Description
 * @Author chenxu
 * @Date 2023/11/16 19:46
 **/

@Configuration
public class ElasticSearchConfig {

    // 创建ES客户端对象 RestHighLevelClient 添加配置信息，注入到Spring容器中
    @Bean
    public RestHighLevelClient esRestClient(){
        // ES连接地址，集群写多个
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http"));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}
