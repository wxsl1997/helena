package com.wxsl.helena.mq.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitDirectConfiguration {

    public static final String DIRECT_QUEUE = "HELENA_DIRECT_QUEUE";

    public static final String DIRECT_ROUTING_KEY = "helena.direct";

    public static final String DIRECT_EXCHANGE = "helena.direct";

    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }


    @Bean
    Binding bindingDirectMessage(@Qualifier("directQueue") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DIRECT_ROUTING_KEY);
    }

    @RabbitListener(queues = DIRECT_QUEUE, errorHandler = "directRabbitListenerErrorHandler")
    public void processDirectQueue(String message) {
        log.info("process direct queue message:{}", message);
    }

    @Bean
    public RabbitListenerErrorHandler directRabbitListenerErrorHandler() {
        return (amqpMessage, message, exception) -> {
            log.info("amqpMessage:{},message:{},exception:{}", amqpMessage, message, exception);
            return exception;
        };
    }
}
