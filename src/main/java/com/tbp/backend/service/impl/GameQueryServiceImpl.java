package com.tbp.backend.service.impl;

import com.tbp.backend.dto.GameDto;
import com.tbp.backend.service.GameQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("!test")
@Slf4j
public class GameQueryServiceImpl implements GameQueryService {
    @Override
    public List<GameDto> findAll() {
        return List.of();
    }

    @Override
    public GameDto findById(String id) {
        return null;
    }
}
