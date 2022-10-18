package com.swave.twitter.elastic.query.service.service.impl;

import com.swave.twitter.elastic.config.model.index.impl.TwitterIndexModel;
import com.swave.twitter.elastic.query.client.service.ElasticQueryClient;
import com.swave.twitter.elastic.query.service.model.response.ElasticQueryServiceResponse;
import com.swave.twitter.elastic.query.service.service.ElasticSearchDocumentService;
import com.swave.twitter.elastic.query.service.transformer.ElasticSearchResponseModelTransformer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticSearchDocumentServiceImpl implements ElasticSearchDocumentService {
    private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;
    private final ElasticSearchResponseModelTransformer transformer;

    public ElasticSearchDocumentServiceImpl(ElasticQueryClient<TwitterIndexModel> elasticQueryClient, ElasticSearchResponseModelTransformer transformer) {
        this.elasticQueryClient = elasticQueryClient;
        this.transformer = transformer;
    }


    @Override
    public List<ElasticQueryServiceResponse> getAll() {
        return transformer.transform(elasticQueryClient.getAllIndexModel());
    }

    @Override
    public ElasticQueryServiceResponse getById(final String id) {
        return transformer.transform(elasticQueryClient.getIndexModelById(id));
    }

    @Override
    public List<ElasticQueryServiceResponse> getByText(final String text) {
        return transformer.transform(elasticQueryClient.getIndexModelByText(text));
    }

}
