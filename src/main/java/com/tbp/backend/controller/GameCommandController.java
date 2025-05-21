package com.tbp.backend.controller;

import com.tbp.backend.dto.CreateGameDto;
import com.tbp.backend.dto.GameDto;
import com.tbp.backend.service.GameCommandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Trigger if new steamId is set")
    @PostMapping("/loadGameList")
    public ResponseEntity<String> triggerNewSteamIdSet(@RequestBody @NotNull String steamId)
    {
        service.loadGameListFromSteam(steamId);
        return ResponseEntity.accepted().body("Processing accepted for ID: " + steamId);
    }
}
