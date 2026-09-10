package com.app.sberjv4_reliabilty2.app.services;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetryService {
    @Autowired
    RetryRegistry registry;

    @Getter
    Retry retry;

    @PostConstruct
    void init() {
        retry = registry.retry("recommendationRetry");
    }
}
