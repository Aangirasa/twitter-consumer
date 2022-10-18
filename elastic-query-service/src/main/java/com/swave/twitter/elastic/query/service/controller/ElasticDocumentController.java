package com.swave.twitter.elastic.query.service.controller;

import com.swave.twitter.elastic.query.service.model.request.ElasticQuerySearchRequestModel;
import com.swave.twitter.elastic.query.service.model.response.ElasticQueryServiceResponse;
import com.swave.twitter.elastic.query.service.service.ElasticSearchDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all the documents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ElasticQueryServiceResponse.class)))
            }),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "500", description = "InternalServerError")
    })
    @GetMapping
    public List<ElasticQueryServiceResponse> getAllDocuments() {
        return elasticSearchDocumentService.getAll();
    }

    @Operation(summary = "Get document by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(schema = @Schema(implementation = ElasticQueryServiceResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "500", description = "InternalServerError")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ElasticQueryServiceResponse getById(@PathVariable("id") final String id) {
        return elasticSearchDocumentService.getById(id);
    }

    @Operation(summary = "Get document by text")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ElasticQueryServiceResponse.class)))
            }),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "500", description = "InternalServerError")
    })
    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ElasticQueryServiceResponse> searchText(@RequestBody @Valid ElasticQuerySearchRequestModel elasticQuerySearchRequest) {
        return elasticSearchDocumentService.getByText(elasticQuerySearchRequest.getText());
    }
}
