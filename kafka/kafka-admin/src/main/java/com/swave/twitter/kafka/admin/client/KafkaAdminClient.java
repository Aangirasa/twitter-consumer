package com.swave.twitter.kafka.admin.client;

import com.swave.twitter.config.KafkaProps;
import com.swave.twitter.config.RetryTemplateProps;
import com.swave.twitter.kafka.admin.error.KafkaClientException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class KafkaAdminClient {
    private final KafkaProps kafkaProps;
    private final RetryTemplateProps retryTemplateProps;
    private final AdminClient adminClient;
    private final RetryTemplate retryTemplate;

    private final WebClient webClient;

    private final Logger log = LoggerFactory.getLogger(KafkaAdminClient.class);


    public KafkaAdminClient(KafkaProps kafkaProps, RetryTemplateProps retryTemplateProps, AdminClient adminClient, RetryTemplate retryTemplate, WebClient webClient) {
        this.kafkaProps = kafkaProps;
        this.retryTemplateProps = retryTemplateProps;
        this.adminClient = adminClient;
        this.retryTemplate = retryTemplate;
        this.webClient = webClient;
    }

    public CreateTopicsResult createTopics() {
        CreateTopicsResult createTopicsResult;
        try {
            createTopicsResult = retryTemplate.execute(this::doCreateTopicsResult);
            log.info("Creating topics for topicNames {}", kafkaProps.getTopicNamesToCreate().toString());
        } catch (Exception e) {
            throw new KafkaClientException("Error occured while trying to create the topics", e);
        }
        return createTopicsResult;
    }

    public CreateTopicsResult doCreateTopicsResult(RetryContext retryContext) {
        List<NewTopic> topics = kafkaProps.getTopicNamesToCreate()
                .stream()
                .map(topicName -> new NewTopic(topicName, kafkaProps.getNumberOfPartitions(), (short) kafkaProps.getReplicationFactor()))
                .collect(Collectors.toList());

        return adminClient.createTopics(topics);
    }

    public void checkTopicsCreated() {
        Collection<TopicListing> topics = getTopics();
        int maxRetry = retryTemplateProps.getMaxAttempts();
        int sleep = retryTemplateProps.getSleepTime();
        int multiplier = retryTemplateProps.getMultiplier();
        try {
            for (TopicListing topicListing : topics) {
                while (!checkSchemaRegistoryStatus().is2xxSuccessful()) {
                    Thread.sleep(sleep);
                    sleep *= multiplier;
                    topics = getTopics();
                }
            }
        } catch (Exception e) {
            throw new KafkaClientException("Error while getting topics ", e);
        }
    }

    public HttpStatus checkSchemaRegistoryStatus() {
        try {
            return webClient.method(HttpMethod.GET)
                    .uri(kafkaProps.getSchemaRegistryUrl())
                    .exchange()
                    .map(ClientResponse::statusCode)
                    .block();
        } catch (Exception e) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
    }

    public Collection<TopicListing> getTopics() {
        try {
            Collection<TopicListing> topics = retryTemplate.execute(this::doGetTopics);
            return topics;
        } catch (Exception e) {
            throw new KafkaClientException("Error occured while trying to get the topics", e);
        }
    }

    public Collection<TopicListing> doGetTopics(RetryContext retryContext) {
        try {
            Collection<TopicListing> listTopicsResult = adminClient.listTopics().listings().get();
            log.debug("Got following topics from the kafka {}", listTopicsResult.toString());
            return listTopicsResult;
        } catch (Exception e) {
            throw new KafkaClientException("Error occured while trying to get the topics", e);
        }
    }
}
