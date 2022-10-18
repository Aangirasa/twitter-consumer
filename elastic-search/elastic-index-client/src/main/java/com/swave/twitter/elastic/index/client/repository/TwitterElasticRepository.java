package com.swave.twitter.elastic.index.client.repository;

import com.swave.twitter.elastic.config.model.index.impl.TwitterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterElasticRepository extends ElasticsearchRepository<TwitterIndexModel, String> {
}
