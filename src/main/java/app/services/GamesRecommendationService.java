package app.services;

import app.adapter.prod.GamesAdapter;
import app.adapter.prod.RecommendationAdapter;
import app.dto.GameResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GamesRecommendationService {
    private final GamesPort gamesClient;

    private final RecommendationPort recommendationClient;

    public GamesRecommendationService(GamesPort gamesClient, RecommendationPort recommendationClient) {
        this.gamesClient = gamesClient;
        this.recommendationClient = recommendationClient;
    }

    public List<Long> getGamesId() {
        return recommendationClient.getRecommendation();
    }

    public GameResponse getGameInfoById(Long gameId) {
        return gamesClient.getGameInfo(gameId);
    }
}
