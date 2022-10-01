package com.swave.twitter.kafka.admin.config;

import com.swave.twitter.config.KafkaProps;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class KafkaAdminConfig {
    private final KafkaProps kafkaProps;

    public KafkaAdminConfig(KafkaProps kafkaProps) {
        this.kafkaProps = kafkaProps;
    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                kafkaProps.getBootstrapServers()));
    }
}
