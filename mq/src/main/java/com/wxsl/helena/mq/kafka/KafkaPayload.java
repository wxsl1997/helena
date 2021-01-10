package com.wxsl.helena.mq.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wxsl1997
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息类型
     */
    String type;

    /**
     * 内容
     */
    String content;
}
