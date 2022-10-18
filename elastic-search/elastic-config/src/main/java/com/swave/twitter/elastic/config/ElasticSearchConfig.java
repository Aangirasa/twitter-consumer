package com.swave.twitter.elastic.config;

import co.elastic.clients.elasticsearch.async_search.ElasticsearchAsyncSearchClient;
import com.swave.twitter.config.ElasticConfigProps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@Configuration
@EnableElasticsearchRepositories(basePackages = "com.swave.twitter.elastic.index.client.repository")
public class ElasticSearchConfig{
    private final ElasticConfigProps elasticConfigProps;

    public ElasticSearchConfig(ElasticConfigProps elasticConfigProps) {
        this.elasticConfigProps = elasticConfigProps;
    }


    @Bean
    public RestClients.ElasticsearchRestClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticConfigProps.getConnectionUrl())
                .build();
        return RestClients.create(clientConfiguration);
    }


}
