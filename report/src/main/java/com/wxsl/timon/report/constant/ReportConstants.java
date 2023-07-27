package com.wxsl.timon.report.constant;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * @author zr
 **/
public interface ReportConstants {

    String HEADER_TOKEN_PROPERTY = "token";

    String TOKEN = "9106c7c1-c9e2-4ec0-916c-f17407699228";

    String AUTHORIZATION = "Authorization";

    String HEADER_TENANT_ID_PROPERTY = "tenantId";

    String TENANT_ID = "932665ee4f44499c8a1ba60143b1106a";

    String HEADER_USER_ID_PROPERTY = "userId";

    String USER_ID = "aad4d3c7b0c7e95c68c76b8ba77375e8";

    String HEADER_USERNAME_PROPERTY = "username";

    String DEFAULT_USERNAME = "admin";

    String HEADER_X_REAL_IP_PROPERTY = "X-Real-IP";

    String HEADER_REQUEST_ID_PROPERTY = "requestId";

    List<String> PAGE_DATA_KEYS = ImmutableList.<String>builder()
            .add("content")
            .add("rows")
            .build();

    List<String> PAGE_TOTAL_KEYS = ImmutableList.<String>builder()
            .add("totalElements")
            .add("total")
            .build();

    List<String> TOTAL_PAGE_KEYS = ImmutableList.<String>builder()
            .add("totalPages")
            .build();
}
