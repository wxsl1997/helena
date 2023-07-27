package com.wxsl.timon.report;

import com.wxsl.timon.report.enhance.ReportHandlerInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * @author zr
 **/

@Configuration
@ComponentScan({"org.jeecg.modules.jmreport"})
public class ReportConfig implements WebMvcConfigurer {

    @Resource
    ReportHandlerInterceptor reportHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(reportHandlerInterceptor).addPathPatterns("/jmreport/**").excludePathPatterns("/jmreport/desreport_/**");
    }
}
