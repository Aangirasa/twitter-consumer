package com.swave.twitter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("elastic-query-service")
public class ElasticQueryServiceConfigProps {
    private String version;
}
