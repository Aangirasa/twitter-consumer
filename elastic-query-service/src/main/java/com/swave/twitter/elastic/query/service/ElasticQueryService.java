package com.swave.twitter.elastic.query.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.swave.twitter")
@SpringBootApplication(exclude = {ReactiveElasticsearchRestClientAutoConfiguration.class})
public class ElasticQueryService {
    public static void main(String[] args) {
        SpringApplication.run(ElasticQueryService.class, args);
    }
}
