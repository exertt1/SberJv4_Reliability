package com.app.sberjv4_reliabilty2.app.services;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CircuitBreakerService {
    @Autowired
    private CircuitBreakerRegistry registry;

    @Getter
    private CircuitBreaker circuitBreaker;

    @PostConstruct
    void init() {
        if (registry.find("recommendationCircuitBreaker").isEmpty()) {
            System.err.println("❌ CircuitBreaker 'recommendationCircuitBreaker' НЕ НАЙДЕН!");
            System.err.println("   Доступные CB: " + registry.getAllCircuitBreakers()
                            .stream()
                    .map(CircuitBreaker::getName)
                    .toList());
        } else {
            this.circuitBreaker = registry.circuitBreaker("recommendationCircuitBreaker");
            System.out.println("✅ CircuitBreaker найден: " + circuitBreaker.getName());
            System.out.println("   State: " + circuitBreaker.getState());
            System.out.println("   Sliding window: " + circuitBreaker.getCircuitBreakerConfig().getSlidingWindowSize());
            System.out.println("   Failure rate: " + circuitBreaker.getCircuitBreakerConfig().getFailureRateThreshold());
        }
    }
}
