package com.wxsl.helena.mq.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfiguration {

    public static final String DIRECT_QUEUE = "HELENA_DIRECT_QUEUE";
    public static final String DIRECT_ROUTING_KEY = "helena.direct";
    public static final String DIRECT_EXCHANGE = "helena.direct";

    public static final String TOPIC_QUEUE = "HELENA_TOPIC_QUEUE";
    public static final String TOPIC_ROUTING_KEY = "helena.#";
    public static final String TOPIC_EXCHANGE = "helena.topic";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, RabbitProperties properties) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE, true);
    }

    @Bean
    public Queue topicQueue() {
        return new Queue(TOPIC_QUEUE, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }


    @Bean
    Binding bindingDirectMessage(@Qualifier("directQueue") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DIRECT_ROUTING_KEY);
    }

    @Bean
    Binding bindingTopicMessage(@Qualifier("topicQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TOPIC_ROUTING_KEY);
    }

    @Bean
    public RabbitListenerErrorHandler directRabbitListenerErrorHandler() {
        return (amqpMessage, message, exception) -> {
            log.info("amqpMessage:{},message:{},exception:{}", amqpMessage, message, exception);
            return exception;
        };
    }

    @RabbitListener(queues = DIRECT_QUEUE, errorHandler = "directRabbitListenerErrorHandler")
    public void processDirectQueue(String message) {
        log.info("process direct queue message:{}", message);
    }

    @RabbitListener(queues = TOPIC_QUEUE)
    public void processTopicQueue(String message) {
        log.info("process topic queue message:{}", message);
    }
}
