package com.wxsl.timon.report.config;

import com.alibaba.fastjson.JSONObject;
import com.wxsl.timon.report.constant.ReportConstants;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.jmreport.desreport.render.handler.convert.ApiDataConvertAdapter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author zr
 **/
@Component("pageConvert")
public class PageConvertAdapter implements ApiDataConvertAdapter {

    private static final String DATA = "data";

    private static final String ZERO = "0";

    @Override
    public String getData(JSONObject jsonObject) {

        return Optional.ofNullable(jsonObject)
                .map(o -> (JSONObject) o.get(DATA))
                .map(o -> ReportConstants.PAGE_DATA_KEYS.stream().filter(o::containsKey).findFirst().map(o::get))
                .map(JSONObject::toJSONString)
                .orElse(StringUtils.EMPTY);

    }

    @Override
    public String getLinks(JSONObject jsonObject) {
        return ApiDataConvertAdapter.super.getLinks(jsonObject);
    }

    @Override
    public String getTotal(JSONObject jsonObject) {
        return Optional.ofNullable(jsonObject)
                .map(o -> (JSONObject) o.get(DATA))
                .map(o -> ReportConstants.TOTAL_PAGE_KEYS.stream().filter(o::containsKey).findFirst().map(o::get).map(String::valueOf).orElse(getCount(jsonObject)))
                .orElse(ZERO);
    }

    @Override
    public String getCount(JSONObject jsonObject) {
        return Optional.ofNullable(jsonObject)
                .map(o -> (JSONObject) o.get(DATA))
                .map(o -> ReportConstants.PAGE_TOTAL_KEYS.stream().filter(o::containsKey).findFirst().map(o::get).map(String::valueOf).orElse(ZERO))
                .orElse(ZERO);
    }
}
