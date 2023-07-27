package com.wxsl.timon.report.enhance;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.wxsl.timon.report.constant.ReportConstants.*;

/**
 * @author wxsl1997
 */
@Slf4j
@Component
public class ReportHandlerInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String remoteIp = Optional.ofNullable(request.getHeader(HEADER_X_REAL_IP_PROPERTY)).orElse(request.getRemoteAddr());

        log.info("report handler interceptor pre handle, remoteIp:{}", remoteIp);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Object requestId = request.getAttribute(HEADER_REQUEST_ID_PROPERTY);
        log.info("report handler interceptor after completion");
        MDC.remove(HEADER_USERNAME_PROPERTY);
        super.afterCompletion(request, response, handler, ex);
    }
}
