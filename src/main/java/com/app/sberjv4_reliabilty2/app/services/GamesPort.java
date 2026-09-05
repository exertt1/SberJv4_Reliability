package com.app.sberjv4_reliabilty2.app.services;

import com.app.sberjv4_reliabilty2.app.dto.GameResponse;

public interface GamesPort {
    GameResponse getGameInfo(Long gameId);
}
