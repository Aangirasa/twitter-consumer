package com.swave.twitter.kafka.elastic.service.transformer;

import com.swave.twitter.elastic.config.model.index.impl.TwitterIndexModel;
import com.swave.twitter.kafka.model.TwitterAvroModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TwitterIndexTransformer {
    public TwitterIndexModel convertToTwitterIndexModel(final TwitterAvroModel twitterAvroModel) {
        return TwitterIndexModel.builder()
                .id(String.valueOf(twitterAvroModel.getId()))
                .text(twitterAvroModel.getText().toString())
                .userId(String.valueOf(twitterAvroModel.getUserId()))
               // .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(twitterAvroModel.getCreatedAt()), ZoneId.systemDefault()).toString())
                .build();
    }

    public List<TwitterIndexModel> convertToIndexList(final List<TwitterAvroModel> twitterAvroModels) {
        return twitterAvroModels.stream()
                .map(this::convertToTwitterIndexModel)
                .collect(Collectors.toList());
    }
}
