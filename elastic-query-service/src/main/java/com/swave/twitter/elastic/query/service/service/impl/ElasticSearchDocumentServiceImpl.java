package com.swave.twitter.elastic.query.service.service.impl;

import com.swave.twitter.elastic.config.model.index.impl.TwitterIndexModel;
import com.swave.twitter.elastic.query.client.service.ElasticQueryClient;
import com.swave.twitter.elastic.query.service.assembler.ElasticQueryResponseAssembler;
import com.swave.twitter.elastic.query.service.model.response.ElasticQueryServiceResponse;
import com.swave.twitter.elastic.query.service.service.ElasticSearchDocumentService;
import com.swave.twitter.elastic.query.service.transformer.ElasticSearchResponseModelTransformer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticSearchDocumentServiceImpl implements ElasticSearchDocumentService {
    private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;
    private final ElasticQueryResponseAssembler assembler;

    public ElasticSearchDocumentServiceImpl(ElasticQueryClient<TwitterIndexModel> elasticQueryClient, ElasticQueryResponseAssembler assembler) {
        this.elasticQueryClient = elasticQueryClient;
        this.assembler = assembler;
    }


    @Override
    public List<ElasticQueryServiceResponse> getAll() {
        return assembler.toModelList(elasticQueryClient.getAllIndexModel());
    }

    @Override
    public ElasticQueryServiceResponse getById(final String id) {
        return assembler.toModel(elasticQueryClient.getIndexModelById(id));
    }

    @Override
    public List<ElasticQueryServiceResponse> getByText(final String text) {
        return assembler.toModelList(elasticQueryClient.getIndexModelByText(text));
    }

}
