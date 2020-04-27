package com.wxsl.helena.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.wxsl.helena.elasticsearch.enumeration.TradeRateResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "helena_trade_rate", type = "_doc", createIndex = false)
public class TradeRate {

    /**
     * 交易ID
     */
    @Id
    private Long tid;

    /**
     * 子订单ID
     */
    @Field(type = FieldType.Long)
    private Long oid;

    /**
     * 评价者昵称
     */
    @Field(type = FieldType.Keyword)
    private String nick;

    /**
     * 评价创建时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created;

    /**
     * 商品的数字ID
     */
    @Field(type = FieldType.Long)
    private Long numIid;

    /**
     * 评价内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    /**
     * 评价结果
     */
    @Field(type = FieldType.Keyword)
    private TradeRateResult result;
}
