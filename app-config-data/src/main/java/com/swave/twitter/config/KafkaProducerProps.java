package com.swave.twitter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kafka-producer-configs")
public class KafkaProducerProps {
    private String keySerializerClass;
    private String valueSerializerClass;
    private String compressionType;
    private String ack;
    private int batchSize;
    private int batchSizeBoostsize;
    private int lingerMs;
    private int requestTimeoutMs;
    private int retryCount;
}
