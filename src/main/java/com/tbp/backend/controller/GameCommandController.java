package com.tbp.backend.controller;

import com.tbp.backend.dto.CreateGameDto;
import com.tbp.backend.dto.GameDto;
import com.tbp.backend.service.GameCommandService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameCommandController {
    private final GameCommandService service;

    public GameCommandController(GameCommandService service) {
        this.service = service;
    }

    @Operation(summary = "Register a new game")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto register(@RequestBody CreateGameDto create) {
        return service.create(create);
    }
}
