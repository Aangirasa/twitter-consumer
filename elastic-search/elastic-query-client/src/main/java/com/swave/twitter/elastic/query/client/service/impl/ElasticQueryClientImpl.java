package com.swave.twitter.elastic.query.client.service.impl;

import com.swave.twitter.config.ElasticConfigProps;
import com.swave.twitter.config.ElasticQueryConfigProps;
import com.swave.twitter.elastic.config.model.index.impl.TwitterIndexModel;
import com.swave.twitter.elastic.query.client.service.ElasticQueryClient;
import com.swave.twitter.elastic.query.client.util.QueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticQueryClientImpl implements ElasticQueryClient<TwitterIndexModel> {
    private final Logger log = LoggerFactory.getLogger(ElasticQueryClientImpl.class);
    private final ElasticConfigProps elasticConfigProps;
    private final ElasticQueryConfigProps elasticQueryConfigProps;
    private final ElasticsearchOperations elasticsearchOperations;
    private final QueryUtils<TwitterIndexModel> queryUtils;

    public ElasticQueryClientImpl(ElasticConfigProps elasticConfigProps, ElasticQueryConfigProps elasticQueryConfigProps, ElasticsearchOperations elasticsearchOperations, QueryUtils<TwitterIndexModel> queryUtils) {
        this.elasticConfigProps = elasticConfigProps;
        this.elasticQueryConfigProps = elasticQueryConfigProps;
        this.elasticsearchOperations = elasticsearchOperations;
        this.queryUtils = queryUtils;
    }

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Query query = queryUtils.getSearchQueryById(id);
        SearchHit<TwitterIndexModel> searchHit = elasticsearchOperations.searchOne(query, TwitterIndexModel.class, IndexCoordinates.of(elasticConfigProps.getIndexName()));
        if (searchHit == null) {
            throw new RuntimeException("Entity not found with id " + id);
        }
        return searchHit.getContent();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        Query query = queryUtils.getSearchQueryWithFieldText(elasticQueryConfigProps.getTextField(), text);
        SearchHits<TwitterIndexModel> searchHits = elasticsearchOperations.search(query, TwitterIndexModel.class, IndexCoordinates.of(elasticConfigProps.getIndexName()));
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModel() {
        Query query = queryUtils.getSearchForAll();
        SearchHits<TwitterIndexModel> searchHits = elasticsearchOperations.search(query, TwitterIndexModel.class, IndexCoordinates.of(elasticConfigProps.getIndexName()));
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
