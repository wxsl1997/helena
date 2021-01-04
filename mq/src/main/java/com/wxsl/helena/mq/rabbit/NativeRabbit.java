package com.wxsl.helena.mq.rabbit;

import com.rabbitmq.client.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;


@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NativeRabbit {

    ConnectionFactory connectionFactory;

    NativeProperties nativeProperties;

    @SuppressWarnings("InfiniteLoopStatement")
    public void consume() {
        Channel channel = getChannel();
        //声明队列
        try {
            String queueName = channel.queueDeclare().getQueue();
            //绑定队列, 通过键将队列和交换器绑定起来
            channel.queueBind(queueName, nativeProperties.getExchangeName(), nativeProperties.getRoutingKey());
            while (true) {
                //消费消息
                channel.basicConsume(queueName, false, "", new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        //确认消息
                        channel.basicAck(envelope.getDeliveryTag(), false);

                        String message = new String(body, StandardCharsets.UTF_8);
                        log.info("consume rabbit message:{}", message);
                    }
                });
            }
        } catch (IOException e) {
            log.error("consume rabbit message fail", e);
        }
    }

    public void provide(@Nonnull String message) {
        Channel channel = getChannel();
        try {
            log.info("start provide rabbit message:{}", message);
            channel.basicPublish(nativeProperties.getExchangeName(), nativeProperties.getRoutingKey(), null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("provide rabbit message fail message:{}", message, e);
        }
    }

    private Channel getChannel() {
        Channel channel = null;
        try {
            Connection connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(nativeProperties.getExchangeName(), nativeProperties.getExchangeType(), true);
        } catch (IOException | TimeoutException e) {
            log.error("get rabbit channel fail", e);
        }
        return channel;
    }
}
