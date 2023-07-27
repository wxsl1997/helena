package com.wxsl.timon.report.enhance;

import com.wxsl.timon.report.constant.ReportConstants;
import com.wxsl.timon.report.util.HttpServletRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static com.wxsl.timon.report.constant.ReportConstants.HEADER_REQUEST_ID_PROPERTY;
import static com.wxsl.timon.report.constant.ReportConstants.HEADER_USERNAME_PROPERTY;

/**
 * @author wxsl1997
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReportGenericFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        String requestId = HttpServletRequestUtils.getRequestId(request);
        try {
            // 设置全局 requestId 属性
            request.setAttribute(HEADER_REQUEST_ID_PROPERTY, requestId);

            // mdc 设置 username
            String username = ReportConstants.DEFAULT_USERNAME;
            MDC.put(HEADER_USERNAME_PROPERTY, username);
            // mdc 设置 requestId
            MDC.put(HEADER_REQUEST_ID_PROPERTY, requestId);

            log.info("execute report generic filter do filter");

            // 执行过滤链
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
