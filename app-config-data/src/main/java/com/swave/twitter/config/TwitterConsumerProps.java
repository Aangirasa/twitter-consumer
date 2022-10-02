package com.swave.twitter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties("twitter-consumer")
@Data
public class TwitterConsumerProps {
    private boolean mockEnabled;
    private List<String> topics;
    private int mockMinimumLength;
    private int mockMaximumLength;
    private long mockSleepDuration;
}
