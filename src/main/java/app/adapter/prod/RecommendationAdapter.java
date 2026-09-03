package app.adapter.prod;

import app.services.RecommendationPort;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@Profile("prod")
public class RecommendationAdapter implements RecommendationPort {
    private final RestClient restClient;

    public RecommendationAdapter(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<Long> getRecommendation() {
        return restClient.get()
                .uri("/api/recommendation")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Long>>() {});
    }
}
