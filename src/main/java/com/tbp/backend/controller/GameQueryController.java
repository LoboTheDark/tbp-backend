package com.tbp.backend.controller;

import com.tbp.backend.dto.GameDto;
import com.tbp.backend.service.GameQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
@Slf4j
public class GameQueryController {
    private final GameQueryService service;

    @Operation(summary = "Show all registered games")
    @GetMapping("/showAll/{steamId}")
    public List<GameDto> showAll(@PathVariable String steamId) {
        log.info("Called with steamId {}", steamId);
        return service.showAll(steamId);
    }

    @Operation(summary = "Show a single game by ID")
    @GetMapping("/show/{id}")
    public GameDto showById(@PathVariable String id) {
        return service.findById(id);
    }
}
