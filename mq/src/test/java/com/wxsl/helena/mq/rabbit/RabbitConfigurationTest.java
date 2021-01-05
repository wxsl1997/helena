package com.wxsl.helena.mq.rabbit;

import com.wxsl.helena.base.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;

class RabbitConfigurationTest extends BaseTest {

    @Test
    void directSend() {
        AmqpTemplate amqpTemplate = applicationContext().getBean(AmqpTemplate.class);
        amqpTemplate.convertAndSend(RabbitConfiguration.DIRECT_EXCHANGE, RabbitConfiguration.DIRECT_ROUTING_KEY, "生命之灯因热情而点燃, 生命之舟因拼搏而前行.");
    }

    @Test
    void topicSend() {
        AmqpTemplate amqpTemplate = applicationContext().getBean(AmqpTemplate.class);
        amqpTemplate.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, "helena.topic", "若不给自己设限, 则人生中就没有限制你发挥的藩篱.");
        amqpTemplate.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, "helena.direct", "凉风把枫叶吹红, 冷言让强者成熟.");
        amqpTemplate.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, "helena.fanout", "未曾失败的人恐怕也未曾成功过.");
    }
}