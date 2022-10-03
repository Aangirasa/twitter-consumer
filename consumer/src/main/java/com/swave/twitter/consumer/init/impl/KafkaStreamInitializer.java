package com.swave.twitter.consumer.init.impl;

import com.swave.twitter.config.KafkaProps;
import com.swave.twitter.consumer.init.StreamInitializer;
import com.swave.twitter.kafka.admin.client.KafkaAdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamInitializer implements StreamInitializer {
    private final KafkaProps kafkaProps;
    private final KafkaAdminClient kafkaAdminClient;

    private final Logger log = LoggerFactory.getLogger(KafkaStreamInitializer.class);

    public KafkaStreamInitializer(KafkaProps kafkaProps, KafkaAdminClient kafkaAdminClient) {
        this.kafkaProps = kafkaProps;
        this.kafkaAdminClient = kafkaAdminClient;
    }

    @Override
    public void init() {
        log.info("Creating Kafka Topics {}", kafkaProps.getTopicNamesToCreate());
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistoryStatus();
        kafkaAdminClient.checkTopicsCreated();
        log.info("Kafka topics created");
    }
}
