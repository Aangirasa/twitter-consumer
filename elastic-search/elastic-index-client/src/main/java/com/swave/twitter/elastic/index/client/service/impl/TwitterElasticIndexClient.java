package com.swave.twitter.elastic.index.client.service.impl;

import com.swave.twitter.elastic.index.client.service.ElasticIndexClient;
import com.swave.twitter.elastic.index.client.util.ElasticIndexUtils;
import com.swave.twitter.config.ElasticConfigProps;
import com.swave.twitter.elastic.config.model.index.impl.TwitterIndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "elastic-search-configs.use-spring-repository", havingValue = "false")
public class TwitterElasticIndexClient implements ElasticIndexClient<TwitterIndexModel> {
    private final ElasticConfigProps elasticConfigProps;
    private final ElasticIndexUtils<TwitterIndexModel> elasticIndexUtils;
    private final ElasticsearchOperations elasticsearchOperations;

    private final Logger log = LoggerFactory.getLogger(TwitterElasticIndexClient.class);

    public TwitterElasticIndexClient(ElasticConfigProps elasticConfigProps, ElasticIndexUtils<TwitterIndexModel> elasticIndexUtils, ElasticsearchOperations elasticsearchOperations) {
        this.elasticConfigProps = elasticConfigProps;
        this.elasticIndexUtils = elasticIndexUtils;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<IndexQuery> indexQueries = elasticIndexUtils.getIndexQueries(documents);
        List<String> documentIds = elasticsearchOperations.bulkIndex(indexQueries,
                        IndexCoordinates.of(elasticConfigProps.getIndexName()))
                .stream()
                .map(i -> i.getId())
                .collect(Collectors.toList());
        log.info("Saved data to elastic index {} , with ids {}", elasticConfigProps.getIndexName(), documentIds);
        return documentIds;
    }
}
