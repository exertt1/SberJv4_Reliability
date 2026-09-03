package app.services;

import app.dto.GameResponse;

public interface GamesPort {
    GameResponse getGameInfo(Long gameId);
}
