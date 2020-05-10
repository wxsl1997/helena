package com.wxsl.helena.elasticsearch.config;

import java.util.Arrays;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EsConfiguration {

    @Bean
    @Primary
    RestHighLevelClient restHighLevelClient(EsProperties esProperties) {
        HttpHost[] httpHosts = Arrays.stream(esProperties.getHosts())
                .map(host -> new HttpHost(host, esProperties.getPort()))
                .toArray(HttpHost[]::new);
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
