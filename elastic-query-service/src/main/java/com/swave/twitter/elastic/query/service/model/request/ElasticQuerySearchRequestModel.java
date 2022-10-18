package com.swave.twitter.elastic.query.service.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElasticQuerySearchRequestModel {
    private String id;
    @NotEmpty
    private String text;
}
