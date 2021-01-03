package com.wxsl.helena.elasticsearch.model;

import java.time.LocalDateTime;
import java.util.List;

import com.wxsl.helena.elasticsearch.enumeration.TradeRateResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeRate {

    /**
     * 交易ID
     */
    private Long tid;

    /**
     * 子订单ID
     */
    private List<Long> oid;

    /**
     * 评价者昵称
     */
    private String nick;

    /**
     * 评价创建时间
     */
    private LocalDateTime created;

    /**
     * 商品的数字ID
     */
    private Long numIid;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价结果
     */
    private TradeRateResult result;
}
