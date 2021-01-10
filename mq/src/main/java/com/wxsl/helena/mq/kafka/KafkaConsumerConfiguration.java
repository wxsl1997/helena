package com.wxsl.helena.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.List;

@Slf4j
@Configuration
public class KafkaConsumerConfiguration {

    private static final String HELENA_GROUP_ID = "helenaGroup";

    public static final String HELENA_TOPIC = "helena.topic";

    public static final String HELENA_FORWARD_TOPIC = "helena.forward.topic";

    @Bean
    public NewTopic helenaTopic() {
        return new NewTopic(HELENA_TOPIC, 8, (short) 1);
    }

    @Bean
    public NewTopic helenaForwardTopic() {
        return new NewTopic(HELENA_FORWARD_TOPIC, 8, (short) 1);
    }


    @Bean
    KafkaListenerErrorHandler kafkaListenerErrorHandler() {
        return (message, exception) -> {
            log.error("failed consume kafka message:{}", message, exception);
            throw exception;
        };
    }

    @Bean
    RecordFilterStrategy<String, Object> recordFilterStrategy() {
        //返回 true 都是时候表示抛弃
        return consumerRecord -> (consumerRecord.offset() & 1) == 0;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(ConsumerFactory<String, Object> consumerFactory,
                                                                                       KafkaTemplate<?, ?> kafkaTemplate,
                                                                                       RecordFilterStrategy<String, Object> recordFilterStrategy) {
        ConcurrentKafkaListenerContainerFactory<String, Object> container = new ConcurrentKafkaListenerContainerFactory<>();
        container.setConsumerFactory(consumerFactory);
        container.setBatchListener(true);
        container.setConcurrency(5);
        container.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        container.getContainerProperties().setMissingTopicsFatal(false);
        container.setReplyTemplate(kafkaTemplate);
        /*container.setAutoStartup(false);*/
        container.setAckDiscarded(true);
        container.setRecordFilterStrategy(recordFilterStrategy);
        return container;
    }

    @KafkaListener(id = HELENA_TOPIC, groupId = HELENA_GROUP_ID, topics = HELENA_TOPIC, errorHandler = "kafkaListenerErrorHandler", containerFactory = "kafkaListenerContainerFactory")
    public void consumerTopic(List<ConsumerRecord<?, ?>> consumerRecords, Acknowledgment acknowledgment) {
        log.info("consume kafka message:{}", consumerRecords);
        acknowledgment.acknowledge();
    }

    @KafkaListener(id = HELENA_FORWARD_TOPIC, groupId = HELENA_GROUP_ID, topics = HELENA_FORWARD_TOPIC, errorHandler = "kafkaListenerErrorHandler", containerFactory = "kafkaListenerContainerFactory")
    @SendTo(HELENA_TOPIC)
    public Object forward(String message, Acknowledgment acknowledgment) {
        log.info("forward kafka message:{}", message);
        acknowledgment.acknowledge();
        return message;
    }
}
