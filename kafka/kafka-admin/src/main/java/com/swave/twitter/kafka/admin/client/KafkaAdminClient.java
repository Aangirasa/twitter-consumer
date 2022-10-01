package com.swave.twitter.kafka.admin.client;

import com.swave.twitter.config.KafkaProps;
import com.swave.twitter.config.RetryTemplateProps;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.retry.support.RetryTemplate;

public class KafkaAdminClient {
    private final KafkaProps kafkaProps;
    private final RetryTemplateProps retryTemplateProps;
    private final AdminClient adminClient;
    private final RetryTemplate retryTemplate;


    public KafkaAdminClient(KafkaProps kafkaProps, RetryTemplateProps retryTemplateProps, AdminClient adminClient, RetryTemplate retryTemplate) {
        this.kafkaProps = kafkaProps;
        this.retryTemplateProps = retryTemplateProps;
        this.adminClient = adminClient;
        this.retryTemplate = retryTemplate;
    }
}
