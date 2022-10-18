package com.swave.twitter.elastic.index.client.service.impl;

import com.swave.twitter.elastic.config.model.index.impl.TwitterIndexModel;
import com.swave.twitter.elastic.index.client.repository.TwitterElasticRepository;
import com.swave.twitter.elastic.index.client.service.ElasticIndexClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "elastic-search-configs.use-spring-repository", havingValue = "true", matchIfMissing = true)
public class TwitterElasticRepositoryClient implements ElasticIndexClient<TwitterIndexModel> {
    private final Logger log = LoggerFactory.getLogger(TwitterElasticRepositoryClient.class);
    private final TwitterElasticRepository twitterElasticRepository;

    public TwitterElasticRepositoryClient(TwitterElasticRepository twitterElasticRepository) {
        this.twitterElasticRepository = twitterElasticRepository;
    }

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<TwitterIndexModel> records = (List<TwitterIndexModel>) twitterElasticRepository.saveAll(documents);
        List<String> ids = records.stream()
                .map(TwitterIndexModel::getId)
                .collect(Collectors.toList());
        log.info("Saved to elastic repository with ids {}", ids);
        return ids;
    }
}
