package com.swave.twitter.kafka.elastic.service.consumer.impl;

import com.swave.twitter.config.KafkaConsumerProps;
import com.swave.twitter.config.KafkaProps;
import com.swave.twitter.elastic.config.model.index.impl.TwitterIndexModel;
import com.swave.twitter.elastic.index.client.service.ElasticIndexClient;
import com.swave.twitter.kafka.admin.client.KafkaAdminClient;
import com.swave.twitter.kafka.elastic.service.consumer.KafkaConsumer;
import com.swave.twitter.kafka.elastic.service.transformer.TwitterIndexTransformer;
import com.swave.twitter.kafka.model.TwitterAvroModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableKafka
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TwitterAvroModel> {

    private final Logger log = LoggerFactory.getLogger(TwitterKafkaConsumer.class);
    private final KafkaProps kafkaProps;
    private final KafkaConsumerProps kafkaConsumerProps;

    private final KafkaAdminClient adminClient;

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private final ElasticIndexClient<TwitterIndexModel> elasticIndexClient;

    private final TwitterIndexTransformer twitterIndexTransformer;

    public TwitterKafkaConsumer(KafkaProps kafkaProps, KafkaConsumerProps kafkaConsumerProps, KafkaAdminClient adminClient, KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry, ElasticIndexClient elasticIndexClient, TwitterIndexTransformer twitterIndexTransformer) {
        this.kafkaProps = kafkaProps;
        this.kafkaConsumerProps = kafkaConsumerProps;
        this.adminClient = adminClient;
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.elasticIndexClient = elasticIndexClient;
        this.twitterIndexTransformer = twitterIndexTransformer;
    }

    @EventListener
    public void kafkaListenerInit(ApplicationStartedEvent event) {
        adminClient.checkTopicsCreated();
        log.info("Kafka topics up, Starting kafka listener");
        kafkaListenerEndpointRegistry.getListenerContainer("twitter-topic-consumer").start();
    }

    @Override
    @KafkaListener(id = "twitter-topic-consumer", topics = {"twitter-topic"})
    public void receive(@Payload List<TwitterAvroModel> messages
            , @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Long> keys
            , @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions
            , @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("Received kafka message from topic message : {} , key: {}, offset: {}"
                , messages
                , keys
                , offsets);
        List ids = elasticIndexClient.save(twitterIndexTransformer.convertToIndexList(messages));
        log.info("Kafka message saved to elastic index {}", ids);
    }
}
