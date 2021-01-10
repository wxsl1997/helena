package com.wxsl.helena.mq.kafka;

import com.wxsl.helena.mq.util.JsonUtils;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class HelenaJsonSerializer implements Serializer<Object> {
    private String encoding = "UTF8";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String propertyName = isKey ? "key.serializer.encoding" : "value.serializer.encoding";
        Object encodingValue = configs.get(propertyName);
        if (encodingValue == null) {
            encodingValue = configs.get("serializer.encoding");
        }
        if (encodingValue instanceof String) {
            encoding = (String) encodingValue;
        }
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        try {
            if (data == null) {
                return null;
            } else {
                return JsonUtils.toJson(data).getBytes(encoding);
            }
        } catch (UnsupportedEncodingException e) {
            throw new SerializationException("Error when serializing string to byte[] due to unsupported encoding " + encoding);
        }
    }
}