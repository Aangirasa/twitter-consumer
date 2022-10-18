package com.swave.twitter.elastic.query.service.transformer;

import com.swave.twitter.elastic.config.model.index.impl.TwitterIndexModel;
import com.swave.twitter.elastic.query.service.model.response.ElasticQueryServiceResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticSearchResponseModelTransformer {

    public ElasticQueryServiceResponse transform(TwitterIndexModel indexModel) {
        return ElasticQueryServiceResponse.builder()
                .id(indexModel.getId())
                .text(indexModel.getText())
                .userId(indexModel.getUserId())
                .build();
    }

    public List<ElasticQueryServiceResponse> transform(List<TwitterIndexModel> indexModels) {
        return indexModels.stream()
                .map(this::transform)
                .collect(Collectors.toList());
    }
}
