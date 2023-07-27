package com.wxsl.timon.report.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zr
 **/
@Configuration
public class ReportDataSourceConfiguration {

    /**
     * report 自定义 数据源配置类 bean name 只能是 minidaoDataSource
     */
    @Bean(name = "minidaoDataSource")
    @ConfigurationProperties(prefix = "jeecg.minidao-datasource")
    public DataSource minidaoDataSource() {
        return new HikariDataSource();
    }
}
