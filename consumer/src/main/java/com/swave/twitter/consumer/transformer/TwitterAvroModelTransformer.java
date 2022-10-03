package com.swave.twitter.consumer.transformer;

import com.swave.twitter.kafka.model.TwitterAvroModel;
import org.springframework.stereotype.Component;
import twitter4j.Status;

@Component
public class TwitterAvroModelTransformer {

    public TwitterAvroModel transform(Status status) {
        return TwitterAvroModel.newBuilder()
                .setId(status.getId())
                .setText(status.getText())
                .setUserId(status.getUser().getId())
                .setCreatedAt(status.getCreatedAt().getTime())
                .build();
    }
}
