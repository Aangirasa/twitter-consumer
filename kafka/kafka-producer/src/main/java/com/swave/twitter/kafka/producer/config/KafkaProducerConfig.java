package com.swave.twitter.kafka.producer.config;

import com.swave.twitter.config.KafkaProducerProps;
import com.swave.twitter.config.KafkaProps;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig<K extends Serializable, V extends SpecificRecordBase> {

    private final KafkaProducerProps kafkaProducerProps;
    private final KafkaProps kafkaProps;

    public KafkaProducerConfig(KafkaProducerProps kafkaProducerProps, KafkaProps kafkaProps) {
        this.kafkaProducerProps = kafkaProducerProps;
        this.kafkaProps = kafkaProps;
    }

    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getBootstrapServers());
        config.put(kafkaProps.getSchemaRegistryKey(), kafkaProps.getSchemaRegistryUrl());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerProps.getValueSerializerClass());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerProps.getValueSerializerClass());
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerProps.getBatchSize() * kafkaProducerProps.getBatchSizeBoostFactor());
        config.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerProps.getLingerMs());
        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerProps.getCompressionType());
        config.put(ProducerConfig.ACKS_CONFIG, kafkaProducerProps.getAck());
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerProps.getRequestTimeoutMs());
        config.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerProps.getRetryCount());
        return config;
    }

    @Bean
    public ProducerFactory producerFactory() {
        return new DefaultKafkaProducerFactory(producerConfig());
    }

    @Bean
    public KafkaTemplate kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }

}
