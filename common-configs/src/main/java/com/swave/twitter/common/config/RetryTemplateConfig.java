package com.swave.twitter.common.config;

import com.swave.twitter.config.RetryTemplateProps;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
public class RetryTemplateConfig {

    private final RetryTemplateProps retryTemplateProps;

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate template = new RetryTemplate();

        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(retryTemplateProps.getInitialInterval());
        exponentialBackOffPolicy.setMaxInterval(retryTemplateProps.getMaxInterval());
        exponentialBackOffPolicy.setMultiplier(retryTemplateProps.getMultiplier());
        template.setBackOffPolicy(exponentialBackOffPolicy);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(retryTemplateProps.getMaxAttempts());
        template.setRetryPolicy(simpleRetryPolicy);

        return template;
    }
}
