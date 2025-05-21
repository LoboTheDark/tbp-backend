package com.tbp.backend.controller;
import com.tbp.backend.dto.CreateGameDto;
import com.tbp.backend.dto.GameDto;
import com.tbp.backend.exception.GameNotFoundException;
import com.tbp.backend.service.impl.InMemoryGameService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerUnitTest {
    private final InMemoryGameService service = new InMemoryGameService();
    private final GameQueryController queryController = new GameQueryController(service);
    private final GameCommandController commandController = new GameCommandController(service);

    @Test
    void registerAndShow() {
        // Register a new game
        CreateGameDto create = new CreateGameDto("Zelda");
        GameDto created = commandController.register(create);

        assertNotNull(created.id());
        assertEquals("Zelda", created.name());

        // Show by ID
        GameDto found = queryController.showById(created.id());
        assertEquals(created.id(), found.id());
    }

    @Test
    void showById_NotFound_Throws() {
        assertThrows(GameNotFoundException.class,
                () -> queryController.showById("does-not-exist"));
    }

}
