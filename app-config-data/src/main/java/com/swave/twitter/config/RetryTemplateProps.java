package com.swave.twitter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("retry-template-configs")
public class RetryTemplateProps {
    private int initialInterval;
    private int maxInterval;
    private int multiplier;
    private int maxAttempts;
    private int sleepTime;
}
