package com.tbp.backend.controller;

import com.tbp.backend.dto.CreateGameDto;
import com.tbp.backend.dto.GameDto;
import com.tbp.backend.exception.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
        // Replacing all default listener
        value = {
                ServletTestExecutionListener.class,
                DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class
        },
        mergeMode = MergeMode.REPLACE_DEFAULTS
)
class GameControllerIntegrationTest {

    @Autowired
    TestRestTemplate rest;

    @Test
    void registerThenShowAllAndById() {
        // 1) Register
        CreateGameDto create = new CreateGameDto("Mario");
        ResponseEntity<GameDto> postResp =
                rest.postForEntity("/api/games/register", create, GameDto.class);
        assertThat(postResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        GameDto created = postResp.getBody();
        assertThat(created).isNotNull()
                .extracting(GameDto::name)
                .isEqualTo("Mario");

        // 2) Show All
        ResponseEntity<GameDto[]> getAll =
                rest.getForEntity("/api/games/showAll", GameDto[].class);
        assertThat(getAll.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getAll.getBody()).hasSize(1)
                .allSatisfy(g -> assertThat(g.name()).isEqualTo("Mario"));

        // 3) Show by ID
        ResponseEntity<GameDto> getById =
                rest.getForEntity("/api/games/show/" + created.id(), GameDto.class);
        assertThat(getById.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getById.getBody()).isNotNull();
        assertThat(getById.getBody().id()).isEqualTo(created.id());
    }

    @Test
    void showById_NotFound_Returns404() {
        ResponseEntity<ErrorResponse> resp =
                rest.getForEntity("/api/games/show/does-not-exist", ErrorResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(resp.getBody())
                .extracting(ErrorResponse::code, ErrorResponse::message)
                .containsExactly("GAME_NOT_FOUND", "Game not found with id: does-not-exist");
    }
}
