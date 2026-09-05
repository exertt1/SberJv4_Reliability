package com.app.sberjv4_reliabilty2.app.adapter.prod;

import com.app.sberjv4_reliabilty2.app.dto.GameResponse;
import com.app.sberjv4_reliabilty2.app.services.GamesPort;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@Profile("prod")
public class GamesAdapter implements GamesPort {
    private final RestClient restClient;

    public GamesAdapter(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public GameResponse getGameInfo(Long gameId) {
        return restClient.get()
                .uri("/api/games/{id}", gameId)
                .retrieve()
                .body(GameResponse.class);
    }

}
