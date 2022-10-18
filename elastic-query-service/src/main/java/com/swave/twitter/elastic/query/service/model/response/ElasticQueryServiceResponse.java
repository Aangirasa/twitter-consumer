package com.swave.twitter.elastic.query.service.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElasticQueryServiceResponse extends RepresentationModel<ElasticQueryServiceResponse> {
    private String id;
    private String userId;
    private String text;
    private LocalDate createdAt;
}
