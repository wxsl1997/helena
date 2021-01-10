package com.wxsl.helena.mq.kafka;

import com.wxsl.helena.base.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.stream.Stream;

import static com.wxsl.helena.mq.kafka.KafkaConsumerConfiguration.HELENA_TOPIC;

class KafkaConfigurationTest extends BaseTest {

    private static final Long RECORD_NUM = 100_000L;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    public void send() {
        Stream.generate(Math::random).limit(RECORD_NUM).forEach(num -> kafkaTemplate.send(HELENA_TOPIC, new KafkaPayload("test", UUID.randomUUID().toString())));
    }


    @Test
    public void startListener() {
        KafkaListenerEndpointRegistry registry = applicationContext().getBean(KafkaListenerEndpointRegistry.class);
        registry.getListenerContainerIds().forEach(listenerContainerId -> registry.getListenerContainer(listenerContainerId).start());
    }
}