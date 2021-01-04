package com.wxsl.helena.mq.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import lombok.Data;

@Data
public class NativeProperties {

    private String username = "admin";

    private String password = "admin";

    private String host = "localhost";

    private String virtualHost = "/";

    private String exchangeName = "helena.exchange.native";

    private BuiltinExchangeType exchangeType = BuiltinExchangeType.DIRECT;

    private String routingKey = "helena.routing.native";
}
