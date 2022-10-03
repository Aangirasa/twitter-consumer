package com.swave.twitter.consumer.service.impl;

import com.swave.twitter.config.KafkaProps;
import com.swave.twitter.consumer.transformer.TwitterAvroModelTransformer;
import com.swave.twitter.kafka.model.TwitterAvroModel;
import com.swave.twitter.kafka.producer.service.impl.TwitterKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Service
@Slf4j
public class TwitterStatusListener extends StatusAdapter {

    private final TwitterKafkaProducer twitterKafkaProducer;

    private final KafkaProps kafkaProps;

    private final TwitterAvroModelTransformer twitterAvroModelTransformer;

    public TwitterStatusListener(TwitterKafkaProducer twitterKafkaProducer, KafkaProps kafkaProps, TwitterAvroModelTransformer twitterAvroModelTransformer) {
        this.twitterKafkaProducer = twitterKafkaProducer;
        this.kafkaProps = kafkaProps;
        this.twitterAvroModelTransformer = twitterAvroModelTransformer;
    }

    @Override
    public void onStatus(Status status) {
        log.info("Twitter status received with message Text : {}", status.getText());
        TwitterAvroModel twitterAvroModel = twitterAvroModelTransformer.transform(status);
        twitterKafkaProducer.send(kafkaProps.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
    }

}
