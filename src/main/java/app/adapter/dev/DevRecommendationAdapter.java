package app.adapter.dev;

import app.services.RecommendationPort;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@Profile("dev")
public class DevRecommendationAdapter implements RecommendationPort {
    private final RestClient restClient;

    public DevRecommendationAdapter(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<Long> getRecommendation() {
        return restClient.get()
                .uri("/recommendation")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Long>>() {});
    }
}
