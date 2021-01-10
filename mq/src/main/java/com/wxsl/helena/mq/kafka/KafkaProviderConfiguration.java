package com.wxsl.helena.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

@Slf4j
@Configuration
public class KafkaProviderConfiguration {

    @Bean
    public ProducerListener<String, Object> producerListener() {
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

    @Bean
    public ProducerFactory<String, Object> kafkaProducerFactory(KafkaProperties properties, ObjectProvider<DefaultKafkaProducerFactoryCustomizer> customizers) {
        DefaultKafkaProducerFactory<String, Object> factory = new DefaultKafkaProducerFactory<>(properties.buildProducerProperties());

        String transactionIdPrefix = properties.getProducer().getTransactionIdPrefix();
        if (transactionIdPrefix != null) {
            factory.setTransactionIdPrefix(transactionIdPrefix);
        }

        customizers.orderedStream().forEach((customizer) -> customizer.customize(factory));
        return factory;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> kafkaProducerFactory, ProducerListener<String, Object> producerListener) {
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
        kafkaTemplate.setProducerListener(producerListener);
        return kafkaTemplate;
    }
}
