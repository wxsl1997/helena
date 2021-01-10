package com.wxsl.helena.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.ProducerListener;

@Slf4j
@Configuration
public class KafkaProviderConfiguration {

    @Bean
    public ProducerListener<?, ?> producerListener() {
        return new ProducerListener<String, Object>() {
            @Override
            public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
                log.debug("success publish kafka message:{}", producerRecord);
            }

            @Override
            public void onError(ProducerRecord producerRecord, Exception exception) {
                log.error("failed publish kafka message:{}", producerRecord);
            }
        };
    }
}
