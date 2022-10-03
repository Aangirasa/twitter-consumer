package com.swave.twitter.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
@ComponentScan(basePackages = "com.swave.twitter")
public class TwitterConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TwitterConsumerApplication.class, args);
    }
}
