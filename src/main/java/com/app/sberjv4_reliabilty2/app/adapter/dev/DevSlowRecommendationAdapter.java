package com.app.sberjv4_reliabilty2.app.adapter.dev;

import com.app.sberjv4_reliabilty2.app.services.RecommendationPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@Profile("dev")
public class DevSlowRecommendationAdapter implements RecommendationPort {
    private final RestClient restClient;

    public DevSlowRecommendationAdapter(@Qualifier("gameSlowRecommendationRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<Long> getRecommendation() {
        return restClient.get()
                .uri("/recommendations-slow")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Long>>() {});
    }
}
