package com.swave.twitter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("kafka-configs")
public class KafkaProps {
    private String bootstrapServers;
    private String schemaRegistryUrl;
    private String schemaRegistryKey;
    private String topicName;
    private List<String> topicNamesToCreate;
    private int numberOfPartitions;
    private int replicationFactor;
}
