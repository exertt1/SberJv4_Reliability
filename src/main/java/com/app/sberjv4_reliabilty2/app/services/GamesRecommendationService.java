package com.app.sberjv4_reliabilty2.app.services;


import com.app.sberjv4_reliabilty2.app.dto.GameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import java.util.List;

public class GamesRecommendationService {
    private final GamesPort gamesClient;
    private final RecommendationPort slowRecommendationClient;
    private final RecommendationPort recommendationClient;

    public GamesRecommendationService(
            GamesPort gamesClient,
            RecommendationPort recommendationClient,
            RecommendationPort slowRecommendationClient
    ) {
        this.gamesClient = gamesClient;
        this.recommendationClient = recommendationClient;
        this.slowRecommendationClient = slowRecommendationClient;
    }

    public List<Long> getGamesId() {
        return recommendationClient.getRecommendation();
    }

    public List<Long> getGamesIdSlow() {
        return slowRecommendationClient.getRecommendation();
    }

    public GameResponse getGameInfoById(Long gameId) {
        return gamesClient.getGameInfo(gameId);
    }
}
