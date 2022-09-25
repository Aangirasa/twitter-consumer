package com.swave.twitter.consumer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.StatusAdapter;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class TwitterStatusListener extends StatusAdapter {

    @Override
    public void onStatus(Status status) {
        log.info("Twitter status recieved with message");
        log.info("Text : {}", status.getText());
        log.info("Auhtor : {}", status.getUser());
        log.info("Date: {}", status.getCreatedAt());
    }

}
