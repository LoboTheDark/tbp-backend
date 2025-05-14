package com.tbp.backend.controller;

import com.tbp.backend.dto.GameDto;
import com.tbp.backend.service.GameQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameQueryController {
    private final GameQueryService service;

    @Operation(summary = "Show all registered games")
    @GetMapping("/showAll")
    public List<GameDto> showAll() {
        return service.findAll();
    }

    @Operation(summary = "Show a single game by ID")
    @GetMapping("/show/{id}")
    public GameDto showById(@PathVariable String id) {
        return service.findById(id);
    }
}
