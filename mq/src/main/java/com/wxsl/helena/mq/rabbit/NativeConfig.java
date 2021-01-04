package com.wxsl.helena.mq.rabbit;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NativeConfig {

    @Bean
    @ConfigurationProperties("rabbit.native")
    public NativeProperties rabbitProperties() {
        return new NativeProperties();
    }

    @Bean
    public ConnectionFactory rabbitConnectionFactory(NativeProperties properties) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(properties.getUsername());
        factory.setPassword(properties.getPassword());
        factory.setHost(properties.getHost());
        factory.setVirtualHost(properties.getVirtualHost());
        return factory;
    }
}
