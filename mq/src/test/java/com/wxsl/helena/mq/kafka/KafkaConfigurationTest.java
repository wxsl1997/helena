package com.wxsl.helena.mq.kafka;

import com.wxsl.helena.base.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.stream.Stream;

import static com.wxsl.helena.mq.kafka.KafkaConsumerConfiguration.HELENA_FORWARD_TOPIC;
import static com.wxsl.helena.mq.kafka.KafkaConsumerConfiguration.HELENA_TOPIC;

class KafkaConfigurationTest extends BaseTest {

    private static final Long RECORD_NUM = 3L;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    public void send() {
        Stream.generate(Math::random).limit(RECORD_NUM).forEach(num -> kafkaTemplate.send(HELENA_TOPIC, "岁岁常欢愉, 万事皆胜意." + num));
    }

    @Test
    public void forward() {
        Stream.generate(Math::random).limit(RECORD_NUM).forEach(num -> kafkaTemplate.send(HELENA_FORWARD_TOPIC, "生命之灯因热情而点燃, 生命之舟因拼搏而前行." + num));
    }

    @Test
    public void startListener() {
        KafkaListenerEndpointRegistry registry = applicationContext().getBean(KafkaListenerEndpointRegistry.class);
        registry.getListenerContainerIds().forEach(listenerContainerId -> registry.getListenerContainer(listenerContainerId).start());
    }
}