package com.tbp.backend.service;

import com.tbp.backend.dto.CreateGameDto;
import com.tbp.backend.dto.GameDto;

public interface GameCommandService {
    GameDto create(CreateGameDto create);
    void loadGameListFromSteam(String steamId);
}
