package com.app.sberjv4_reliabilty2.app.adapter.dev;

import com.app.sberjv4_reliabilty2.app.services.CircuitBreakerService;
import com.app.sberjv4_reliabilty2.app.services.RecommendationPort;
import com.app.sberjv4_reliabilty2.app.services.RetryService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.function.Supplier;

@Profile("dev")
public class  DevFailRecommendationAdapter implements RecommendationPort {
    private final RestClient restClient;

    private final CircuitBreaker circuitBreaker;

    private final Retry retry;

    public DevFailRecommendationAdapter(
            RestClient restClient,
            CircuitBreakerService circuitBreakerService,
            RetryService retryService
    ) {
        this.restClient = restClient;
        this.circuitBreaker = circuitBreakerService.getCircuitBreaker();
        this.retry = retryService.getRetry();
    }

    @Override
    public List<Long> getRecommendation() throws CallNotPermittedException {
        Supplier<List<Long>> retrySupply = Retry.decorateSupplier(
                retry,
                () -> restClient.get()
                        .uri("/recommendation-fail")
                        .retrieve()
                        .body(new ParameterizedTypeReference<List<Long>>() {
                        })
        );

        Supplier<List<Long>> decorateSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                retrySupply
        );

        return decorateSupplier.get();
    }
}
