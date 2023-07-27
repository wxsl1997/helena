package com.wxsl.timon.report.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.wxsl.timon.report.constant.ReportConstants.HEADER_REQUEST_ID_PROPERTY;

/**
 * @author wxsl1997
 */
public class HttpServletRequestUtils {

    private static final long REQUEST_ID_LOWER_LIMIT = 100_000_000L;

    private static final long REQUEST_ID_UPPER_LIMIT = 999_999_999L;

    public static String getRequestId(ServletRequest request) {
        return Optional.ofNullable(request.getAttribute(HEADER_REQUEST_ID_PROPERTY))
                .map(Object::toString)
                .orElse(String.valueOf(ThreadLocalRandom.current().nextLong(REQUEST_ID_LOWER_LIMIT, REQUEST_ID_UPPER_LIMIT)));
    }

    public static HttpServletRequest getHttpRequest() {
        return Optional
                .ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                .map(ServletRequestAttributes::getRequest)
                .orElseThrow(() -> new RuntimeException("request context not exist request attributes"));
    }
}
