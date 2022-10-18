package com.swave.twitter.elastic.query.service.assembler;

import com.swave.twitter.elastic.config.model.index.impl.TwitterIndexModel;
import com.swave.twitter.elastic.query.service.controller.ElasticDocumentController;
import com.swave.twitter.elastic.query.service.model.response.ElasticQueryServiceResponse;
import com.swave.twitter.elastic.query.service.transformer.ElasticSearchResponseModelTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ElasticQueryResponseAssembler extends RepresentationModelAssemblerSupport<TwitterIndexModel, ElasticQueryServiceResponse> {
    @Autowired
    private ElasticSearchResponseModelTransformer transformer;

    public ElasticQueryResponseAssembler() {
        super(ElasticDocumentController.class, ElasticQueryServiceResponse.class);
    }


    @Override
    public ElasticQueryServiceResponse toModel(TwitterIndexModel entity) {
        ElasticQueryServiceResponse elasticQueryServiceResponse = transformer.transform(entity);
        elasticQueryServiceResponse.add(
                linkTo(methodOn(ElasticDocumentController.class)
                        .getById((elasticQueryServiceResponse.getId()))
                ).withSelfRel());
        elasticQueryServiceResponse.add(
                linkTo(ElasticDocumentController.class)
                        .withRel("documents"));
        return elasticQueryServiceResponse;
    }

    public List<ElasticQueryServiceResponse> toModelList(List<TwitterIndexModel> twitterIndexModels) {
        return twitterIndexModels.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
