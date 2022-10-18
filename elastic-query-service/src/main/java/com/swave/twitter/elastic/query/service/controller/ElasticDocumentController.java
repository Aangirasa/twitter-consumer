package com.swave.twitter.elastic.query.service.controller;

import com.swave.twitter.elastic.query.service.model.request.ElasticQuerySearchRequestModel;
import com.swave.twitter.elastic.query.service.model.response.ElasticQueryServiceResponse;
import com.swave.twitter.elastic.query.service.service.ElasticSearchDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class ElasticDocumentController {
    private final ElasticSearchDocumentService elasticSearchDocumentService;

    public ElasticDocumentController(ElasticSearchDocumentService elasticSearchDocumentService) {
        this.elasticSearchDocumentService = elasticSearchDocumentService;
    }

    @GetMapping
    public List<ElasticQueryServiceResponse> getAllDocuments() {
        return elasticSearchDocumentService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ElasticQueryServiceResponse getById(@PathVariable("id") final String id) {
        return elasticSearchDocumentService.getById(id);
    }

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ElasticQueryServiceResponse> searchText(@RequestBody @Valid ElasticQuerySearchRequestModel elasticQuerySearchRequest) {
        return elasticSearchDocumentService.getByText(elasticQuerySearchRequest.getText());
    }
}
