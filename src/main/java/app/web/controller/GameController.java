package app.web.controller;

import app.services.GamesPort;
import app.services.GamesRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wiremock.org.eclipse.jetty.server.Response;

@RestController
@RequestMapping("/game-store")
public class GameController {
    private final GamesRecommendationService recommendationService;

    public GameController(GamesRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendations")
    public ResponseEntity<?> getRecommendations() {
        return ResponseEntity.ok(recommendationService.getGamesId());
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<?> getGameInfo(@PathVariable Long id) {
        return ResponseEntity.ok(recommendationService.getGameInfoById(id));
    }
}
