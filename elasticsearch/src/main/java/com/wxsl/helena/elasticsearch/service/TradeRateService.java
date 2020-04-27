package com.wxsl.helena.elasticsearch.service;

import com.wxsl.helena.elasticsearch.dao.TradeRateDao;
import com.wxsl.helena.elasticsearch.model.TradeRate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TradeRateService {

    TradeRateDao tradeRateDao;

    public Page<TradeRate> listTradeRate(Pageable pageable) {
        return tradeRateDao.findAll(pageable);
    }
}
