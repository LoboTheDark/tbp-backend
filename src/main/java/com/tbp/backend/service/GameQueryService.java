package com.tbp.backend.service;
import com.tbp.backend.dto.GameDto;
import java.util.List;

public interface GameQueryService {
    List<GameDto> showAll(String steamId);
    GameDto findById(String id);
}

