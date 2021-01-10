package com.wxsl.helena.mq.kafka;

import com.wxsl.helena.mq.util.JsonUtils;
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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class KafkaConsumerConfiguration {

    private static final String HELENA_GROUP_ID = "helena-consumer-group";

    public static final String HELENA_TOPIC = "helena";

    @Bean
    public NewTopic helenaTopic() {
        return new NewTopic(HELENA_TOPIC, 8, (short) 1);
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
        return consumerRecord -> false;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(ConsumerFactory<String, Object> consumerFactory,
                                                                                       KafkaTemplate<?, ?> kafkaTemplate,
                                                                                       RecordFilterStrategy<String, Object> recordFilterStrategy) {
        ConcurrentKafkaListenerContainerFactory<String, Object> container = new ConcurrentKafkaListenerContainerFactory<>();
        container.setConsumerFactory(consumerFactory);
        container.setBatchListener(true);
        container.setConcurrency(10);
        container.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);
        container.getContainerProperties().setMissingTopicsFatal(false);
        container.setReplyTemplate(kafkaTemplate);
        container.setAckDiscarded(true);
        container.setRecordFilterStrategy(recordFilterStrategy);
        return container;
    }

    @KafkaListener(id = HELENA_TOPIC, groupId = HELENA_GROUP_ID, topics = HELENA_TOPIC, errorHandler = "kafkaListenerErrorHandler", containerFactory = "kafkaListenerContainerFactory")
    public void consumerTopic(List<ConsumerRecord<String, String>> consumerRecords) {
        List<KafkaPayload> kafkaPayloads = consumerRecords.stream().map(ConsumerRecord::value).map(value -> JsonUtils.toObject(value, KafkaPayload.class)).collect(Collectors.toList());
        log.info("consume kafka message:{}", kafkaPayloads);
    }
}
