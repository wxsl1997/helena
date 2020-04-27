package com.wxsl.helena.elasticsearch.controller;

import com.wxsl.helena.elasticsearch.model.TradeRate;
import com.wxsl.helena.elasticsearch.service.TradeRateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequiredArgsConstructor
@RequestMapping("/helena/trade-rate/")
public class TradeRateController {

    TradeRateService tradeRateService;

    @GetMapping("list-trade-rate")
    public Page<TradeRate> listTradeRate(Pageable pageable) {
        return tradeRateService.listTradeRate(pageable);
    }
}
