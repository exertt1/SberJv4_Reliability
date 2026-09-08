package com.app.sberjv4_reliabilty2.app.services;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {
    @Autowired
    private RateLimiterRegistry registry;

    private RateLimiter rateLimiter;

    @PostConstruct
    public void init() {
//        RateLimiterConfig config = RateLimiterConfig.custom()
//                .limitForPeriod(5)
//                .limitRefreshPeriod(Duration.ofSeconds(10))
//                .timeoutDuration(Duration.ZERO)
//                .build();

//        RateLimiterRegistry registry = RateLimiterRegistry.of(config);
        rateLimiter = registry.rateLimiter("gameRecommendationRateLimiter");

        System.out.println("RateLimiter инициализирован: 5 запросов за 10 секунд");
    }

    public boolean tryAcquire() {
        return rateLimiter.acquirePermission();
    }
}
