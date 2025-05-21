package com.tbp.backend.service.impl;

import com.tbp.backend.datamodel.readmodel.GameReadRepository;
import com.tbp.backend.dto.GameDto;
import com.tbp.backend.service.GameQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("!test")
@Slf4j
@AllArgsConstructor
public class GameQueryServiceImpl implements GameQueryService {

    private final GameReadRepository repository;

    @Override
    public List<GameDto> showAll(String steamId) {

        var dbResult =  repository.findBySteamIdsContaining(steamId);

        var result = new ArrayList<GameDto>(dbResult.size());

        dbResult.forEach(gameReadModel -> result.add(new GameDto(gameReadModel.getId(), gameReadModel.getTitle(), gameReadModel.getTitleImage())));

        return result;
    }

    @Override
    public GameDto findById(String id) {
        return null;
    }
}
