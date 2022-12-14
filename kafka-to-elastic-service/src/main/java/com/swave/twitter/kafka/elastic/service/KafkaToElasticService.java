package com.swave.twitter.kafka.elastic.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.swave.twitter")
@SpringBootApplication(exclude = {ReactiveElasticsearchRestClientAutoConfiguration.class})
public class KafkaToElasticService {
    public static void main(String[] args) {
        SpringApplication.run(KafkaToElasticService.class, args);
    }
}
