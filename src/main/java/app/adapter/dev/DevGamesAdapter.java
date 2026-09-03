package app.adapter.dev;

import app.dto.GameResponse;
import app.services.GamesPort;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@Profile("dev")
public class DevGamesAdapter implements GamesPort {
    private final RestClient restClient;

    public DevGamesAdapter(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public GameResponse getGameInfo(Long gameId) {
        return restClient.get()
                .uri("/games/{id}", gameId)
                .retrieve()
                .body(GameResponse.class);
    }


}
