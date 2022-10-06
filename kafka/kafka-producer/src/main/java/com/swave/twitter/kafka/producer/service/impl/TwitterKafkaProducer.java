package com.swave.twitter.kafka.producer.service.impl;

import com.swave.twitter.kafka.model.TwitterAvroModel;
import com.swave.twitter.kafka.producer.service.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;

@Service
public class TwitterKafkaProducer implements KafkaProducer<Long, TwitterAvroModel> {
    private final Logger log = LoggerFactory.getLogger(TwitterKafkaProducer.class);

    private final KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;

    public TwitterKafkaProducer(KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, Long key, TwitterAvroModel message) {
        log.info("Sending Kafka message to {},  {} : {}",topicName, key, message);
        ListenableFuture<SendResult<Long, TwitterAvroModel>> result = kafkaTemplate.send(topicName, key, message);
        result.addCallback(createNewCallback());
    }

    private ListenableFutureCallback<SendResult<Long, TwitterAvroModel>> createNewCallback() {
        return new ListenableFutureCallback<SendResult<Long, TwitterAvroModel>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error while sending message to the kafka", ex);
            }

            @Override
            public void onSuccess(SendResult<Long, TwitterAvroModel> result) {
                log.debug("Successfully sent the message to kafka");
                log.debug("Topic {} , partition {}, Offset {}, timestamp {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset(),
                        result.getRecordMetadata().timestamp());
            }
        };
    }

    @PreDestroy
    public void preDestroy() {
        log.info("closing KafkaTemplate");
        kafkaTemplate.destroy();
    }
}
