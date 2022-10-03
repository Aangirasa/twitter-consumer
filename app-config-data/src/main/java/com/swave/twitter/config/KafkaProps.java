package com.swave.twitter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties("kafka-configs")
@Configuration
public class KafkaProps {
    private String bootstrapServers;
    private String schemaRegistryUrl;
    private String schemaRegistryKey;
    private String topicName;
    private List<String> topicNamesToCreate;
    private int numberOfPartitions;
    private int replicationFactor;
}
