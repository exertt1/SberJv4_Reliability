package com.app.sberjv4_reliabilty2.app.web.controller;

import com.app.sberjv4_reliabilty2.app.core.exceptions.RateLimiterExceedException;
import com.app.sberjv4_reliabilty2.app.dto.GameResponse;
import com.app.sberjv4_reliabilty2.app.services.GamesRecommendationService;
import com.app.sberjv4_reliabilty2.app.services.RateLimiterService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wiremock.org.eclipse.jetty.server.Response;

import io.github.resilience4j.ratelimiter.RateLimiter;

import java.util.List;

@RestController
@RequestMapping("/game-store")
public class GameController {
    private final GamesRecommendationService recommendationService;
    private final RateLimiterService rateLimiter;

    public GameController(
            GamesRecommendationService recommendationService,
            RateLimiterService rateLimiter
    ) {
        this.recommendationService = recommendationService;
        this.rateLimiter = rateLimiter;
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<Long>> getRecommendations() throws RequestNotPermitted {
        if (!rateLimiter.tryAcquire()) {
            throw new RateLimiterExceedException("");
        }

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

    public ResponseEntity<String> rateLimiterFallback(Throwable t) {
        return ResponseEntity.
                status(429)
                .body("Преавшен лимит запросов, попробуйте немного позже...");
    }
}
