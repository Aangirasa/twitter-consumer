package com.swave.twitter.elastic.index.client.service;

import com.swave.twitter.elastic.config.model.index.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    List<String> save(List<T> documents);
}
