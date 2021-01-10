package com.wxsl.helena.mq.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitTopicConfiguration {

    public static final String TOPIC_QUEUE = "HELENA_TOPIC_QUEUE";

    public static final String TOPIC_ROUTING_KEY = "helena.#";

    public static final String TOPIC_EXCHANGE = "helena.topic";

    @Bean
    public Queue topicQueue() {
        return new Queue(TOPIC_QUEUE, true);
    }


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }


    @Bean
    Binding bindingTopicMessage(@Qualifier("topicQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TOPIC_ROUTING_KEY);
    }


    @RabbitListener(queues = TOPIC_QUEUE)
    public void processTopicQueue(String message) {
        log.info("process topic queue message:{}", message);
    }
}
