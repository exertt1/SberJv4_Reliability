package com.app.sberjv4_reliabilty2.app.web.controller;

import com.app.sberjv4_reliabilty2.app.dto.GameResponse;
import com.app.sberjv4_reliabilty2.app.services.GamesRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wiremock.org.eclipse.jetty.server.Response;

import java.util.List;

@RestController
@RequestMapping("/game-store")
public class GameController {
    private final GamesRecommendationService recommendationService;

    public GameController(GamesRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<Long>> getRecommendations() {
        return ResponseEntity.ok(recommendationService.getGamesId());
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<GameResponse> getGameInfo(@PathVariable Long id) {
        return ResponseEntity.ok(recommendationService.getGameInfoById(id));
    }

    @GetMapping("/error-test")
    public ResponseEntity<GameResponse> error() throws Exception {
        throw new Exception("Ошибка");
    }

    @GetMapping("/recommendations-slow")
    public ResponseEntity<List<Long>> getRecommendationsSlow() {
        return ResponseEntity.ok(recommendationService.getGamesIdSlow());
    }
}
