package com.wxsl.helena.elasticsearch.dao;

import com.wxsl.helena.elasticsearch.enumeration.TradeRateResult;
import com.wxsl.helena.elasticsearch.model.TradeRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TradeRateDaoTest {

    @Autowired
    private TradeRateDao tradeRateDao;

    @Test
    void save() {
        TradeRate tradeRate = TradeRate.builder()
                .tid(2020042801L)
                .oid(2020042801L)
                .nick("文修森啦")
                .created(LocalDateTime.now())
                .numIid(100001L)
                .content("我以为恐怕灼伤而躲过火焰，却不料在在海水中惨遭灭顶。")
                .result(TradeRateResult.GOOD)
                .build();
        tradeRateDao.save(tradeRate);
    }
}
