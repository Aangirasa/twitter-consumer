package com.swave.twitter.elastic.config.model.index.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swave.twitter.elastic.config.model.index.IndexModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "twitter-index")
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterIndexModel implements IndexModel {
    @JsonProperty
    private String id;
    @JsonProperty
    private String userId;
    @JsonProperty
    private String text;
 /*   @JsonProperty
    private String createdAt;*/
}
