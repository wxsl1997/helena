package com.wxsl.timon.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zr
 **/

@SpringBootApplication(scanBasePackageClasses = {ReportConfig.class})
public class ReportApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ReportApplication.class, args);
        System.out.println(context);
    }
}
