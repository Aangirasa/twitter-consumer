package com.swave.twitter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties("retry-template-configs")
@Configuration
public class RetryTemplateProps {
    private int initialInterval;
    private int maxInterval;
    private int multiplier;
    private int maxAttempts;
    private int sleepTime;
}
