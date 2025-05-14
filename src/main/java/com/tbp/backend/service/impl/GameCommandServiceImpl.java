package com.tbp.backend.service.impl;

import com.tbp.backend.dto.CreateGameDto;
import com.tbp.backend.dto.GameDto;
import com.tbp.backend.external.steam.SteamApiClient;
import com.tbp.backend.service.GameCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Profile("!test")
@Slf4j
public class GameCommandServiceImpl implements GameCommandService {

    private final RestTemplate restTemplate;

    @Value("${steam.api-key}")
    private String steamApiKey;

    @Value("${steam.api-url}")
    private String steamApiUrl;

    public GameCommandServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GameDto create(CreateGameDto create) {
        return null;
    }

    @Async
    @Override
    public void loadGameListFromSteam(String steamId) {
        log.info("REGISTER ME with {} and {}", steamId, steamApiKey);
        var response = SteamApiClient.callSteam(steamApiUrl, steamApiKey, steamId, restTemplate);

        log.info("Response {}", response);
    }
}
