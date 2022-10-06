package com.swave.twitter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "kafka-producer-configs")
@Configuration
public class KafkaProducerProps {
    private String keySerializerClass;
    private String valueSerializerClass;
    private String compressionType;
    private String ack;
    private int batchSize;
    private int batchSizeBoostFactor;
    private int lingerMs;
    private int requestTimeoutMs;
    private int retryCount;
}
