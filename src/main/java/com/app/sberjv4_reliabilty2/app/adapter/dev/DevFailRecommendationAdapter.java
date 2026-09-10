package com.app.sberjv4_reliabilty2.app.adapter.dev;

import com.app.sberjv4_reliabilty2.app.services.CircuitBreakerService;
import com.app.sberjv4_reliabilty2.app.services.RecommendationPort;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.function.Supplier;

@Profile("dev")
public class DevFailRecommendationAdapter implements RecommendationPort {
    private final RestClient restClient;

    private final CircuitBreaker circuitBreaker;

    public DevFailRecommendationAdapter(
            RestClient restClient,
            CircuitBreakerService circuitBreakerService
    ) {
        this.restClient = restClient;
        this.circuitBreaker = circuitBreakerService.getCircuitBreaker();
    }

    @Override
    public List<Long> getRecommendation() throws CallNotPermittedException {
        Supplier<List<Long>> supply = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> {
                    return restClient.get()
                            .uri("/recommendation-fail")
                            .retrieve()
                            .body(new ParameterizedTypeReference<List<Long>>() {
                            });
                }
        );

        return supply.get();
    }
}
