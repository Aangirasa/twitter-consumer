package com.swave.twitter.elastic.query.service.service;

import com.swave.twitter.elastic.query.service.model.response.ElasticQueryServiceResponse;

import java.util.List;

public interface ElasticSearchDocumentService {
    public List<ElasticQueryServiceResponse> getAll();

    public ElasticQueryServiceResponse getById(String id);

    public List<ElasticQueryServiceResponse> getByText(String text);
}
