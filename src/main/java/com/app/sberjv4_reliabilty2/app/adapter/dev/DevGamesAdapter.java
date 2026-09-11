package com.app.sberjv4_reliabilty2.app.adapter.dev;


import com.app.sberjv4_reliabilty2.app.dto.GameResponse;
import com.app.sberjv4_reliabilty2.app.services.GamesPort;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@Profile("dev")
public class DevGamesAdapter implements GamesPort {
    private final RestClient restClient;

    public DevGamesAdapter(@Qualifier("gameRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    @Cacheable(value = "games", key = "#gameId")
    public GameResponse getGameInfo(Long gameId) {
        return restClient.get()
                .uri("/games/{id}", gameId)
                .retrieve()
                .body(GameResponse.class);
    }


}
