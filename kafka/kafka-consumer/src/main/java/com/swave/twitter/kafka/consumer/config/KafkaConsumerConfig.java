package com.swave.twitter.kafka.consumer.config;

import com.swave.twitter.config.KafkaConsumerProps;
import com.swave.twitter.config.KafkaProps;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class KafkaConsumerConfig<K extends Serializable, V extends SpecificRecordBase> {
    private final Logger log = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    private final KafkaProps kafkaProps;
    private final KafkaConsumerProps kafkaConsumerProps;

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public KafkaConsumerConfig(KafkaProps kafkaProps, KafkaConsumerProps kafkaConsumerProps, KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) {
        this.kafkaProps = kafkaProps;
        this.kafkaConsumerProps = kafkaConsumerProps;
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
    }

    @Bean("KafkaConfig")
    public Map<String, Object> consumerConfig() {
        log.info("Got configs{}", kafkaConsumerProps.toString());
        Map<String, Object> configs = new ConcurrentHashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getBootstrapServers());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configs.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, kafkaConsumerProps.getKeyDeserializer());
        configs.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, kafkaConsumerProps.getValueDeserializer());
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProps.getConsumerGroupId());
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerProps.getAutoOffsetReset());
        configs.put(kafkaProps.getSchemaRegistryKey(), kafkaProps.getSchemaRegistryUrl());
        configs.put(kafkaConsumerProps.getSpecificAvroReaderKey(), kafkaConsumerProps.getSpecificAvroReader());
        configs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaConsumerProps.getSessionTimeoutMs());
        configs.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, kafkaConsumerProps.getHeartbeatIntervalMs());
        configs.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, kafkaConsumerProps.getMaxPollIntervalMs());
        configs.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, kafkaConsumerProps.getMaxPartitionFetchBytesDefault()
                * kafkaConsumerProps.getMaxPartitionFetchBytesBoostFactor());
        configs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaConsumerProps.getMaxPollRecords());
        return configs;
    }

    @Bean
    public ConsumerFactory<K, V> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(kafkaConsumerProps.getBatchListener());
        factory.setConcurrency(kafkaConsumerProps.getConcurrencyLevel());
        factory.setAutoStartup(kafkaConsumerProps.getAutoStartup());
        factory.getContainerProperties().setPollTimeout(kafkaConsumerProps.getPollTimeoutMs());
        return factory;


    }
}
