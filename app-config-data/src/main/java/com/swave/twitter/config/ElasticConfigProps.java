package com.swave.twitter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("elastic-search-configs")
public class ElasticConfigProps {
    private String indexName;
    private String connectionUrl;
    private int connectionTimeoutMs;
    private int socketTimeoutMs;
}
