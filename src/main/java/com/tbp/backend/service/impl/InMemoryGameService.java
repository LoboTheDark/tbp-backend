package com.tbp.backend.service.impl;

import com.tbp.backend.dto.CreateGameDto;
import com.tbp.backend.dto.GameDto;
import com.tbp.backend.exception.GameNotFoundException;
import com.tbp.backend.model.Game;
import com.tbp.backend.service.GameCommandService;
import com.tbp.backend.service.GameQueryService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Profile("test")
public class InMemoryGameService implements GameQueryService, GameCommandService {
    private final Map<String, Game> store = new ConcurrentHashMap<>();

    @Override
    public List<GameDto> showAll(String steamId) {
        var list = new ArrayList<GameDto>();
        for (var game : store.values()) {
            list.add(new GameDto(game.getId(), game.getName(), ""));
        }
        return list;
    }

    @Override
    public GameDto findById(String id) {
        var game = store.get(id);
        if (game == null) {
            throw new GameNotFoundException(id);
        }
        return new GameDto(game.getId(), game.getName(), "");
    }

    @Override
    public GameDto create(CreateGameDto create) {
        var id = UUID.randomUUID().toString();
        var game  = new Game(id, create.name());
        store.put(id, game);
        return new GameDto(game.getId(), game.getName(), "");
    }

    @Override
    public void loadGameListFromSteam(String steamId) {
        //nothing to do
    }
}
