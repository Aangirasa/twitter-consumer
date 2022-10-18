package com.swave.twitter.elastic.query.client.service;

import com.swave.twitter.elastic.config.model.index.IndexModel;

import java.util.List;

public interface ElasticQueryClient<T extends IndexModel> {

    T getIndexModelById(String id);

    List<T> getIndexModelByText(String text);

    List<T> getAllIndexModel();
}
