package com.wxsl.timon.report.config;

import com.google.common.collect.Maps;
import com.wxsl.timon.report.constant.ReportConstants;
import com.wxsl.timon.report.util.HttpServletRequestUtils;
import org.jeecg.modules.jmreport.api.JmReportTokenServiceI;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.wxsl.timon.report.constant.ReportConstants.*;

/**
 * @author zr
 **/
@Component
public class JmReportTokenService implements JmReportTokenServiceI {


    @Override
    public String getToken(HttpServletRequest request) {
        //需根据 request 获取token
        return ReportConstants.TOKEN;
    }

    @Override
    public String getUsername(String token) {
        HttpServletRequest request = HttpServletRequestUtils.getHttpRequest();
        // 根据token获取 登录信息
        return ReportConstants.DEFAULT_USERNAME;
    }

    @Override
    public Boolean verifyToken(String token) {
        return true;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        HttpServletRequest request = HttpServletRequestUtils.getHttpRequest();

        HashMap<String, Object> map = Maps.newHashMap();
        // userId
        map.put(HEADER_USER_ID_PROPERTY, USER_ID);
        // tenantId
        map.put(HEADER_TENANT_ID_PROPERTY, TENANT_ID);
        return map;
    }

    @Override
    public HttpHeaders customApiHeader() {
        HttpServletRequest request = HttpServletRequestUtils.getHttpRequest();
        HttpHeaders headers = new HttpHeaders();
        headers.add(ReportConstants.AUTHORIZATION, String.format("Bearer %s", getToken()));
        headers.add(ReportConstants.HEADER_USER_ID_PROPERTY, USER_ID);
        headers.add(ReportConstants.HEADER_USER_ID_PROPERTY, USER_ID);
        return headers;
    }


    @Override
    public String getTenantId() {
        HttpServletRequest request = HttpServletRequestUtils.getHttpRequest();
        return TENANT_ID;
    }

}
