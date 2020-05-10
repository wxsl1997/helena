package com.wxsl.helena.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "es")
@Component
@Data
public class EsProperties {

    String[] hosts = new String[]{"127.0.0.1"};

    Integer port = 9200;
}
