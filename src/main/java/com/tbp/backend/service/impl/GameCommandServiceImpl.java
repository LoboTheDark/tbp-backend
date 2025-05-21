package com.tbp.backend.service.impl;

import com.eventstore.dbclient.AppendToStreamOptions;
import com.eventstore.dbclient.EventData;
import com.eventstore.dbclient.EventStoreDBClient;
import com.tbp.backend.datamodel.eventstore.Events;
import com.tbp.backend.datamodel.eventstore.GamesSyncedEvent;
import com.tbp.backend.dto.CreateGameDto;
import com.tbp.backend.dto.GameDto;
import com.tbp.backend.dto.steam.SteamOwnedGamesResponse;
import com.tbp.backend.external.steam.SteamApiClient;
import com.tbp.backend.service.GameCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.UUID;

@Service
@Profile("!test")
@Slf4j
public class GameCommandServiceImpl implements GameCommandService {

    private final RestTemplate restTemplate;
    private final EventStoreDBClient dbClient;

    @Value("${steam.api-key}")
    private String steamApiKey;

    @Value("${steam.api-url}")
    private String steamApiUrl;

    public GameCommandServiceImpl(RestTemplate restTemplate, EventStoreDBClient dbClient) {
        this.restTemplate = restTemplate;
        this.dbClient = dbClient;
    }

    @Override
    public GameDto create(CreateGameDto create) {
        return null;
    }

    @Async
    @Override
    public void loadGameListFromSteam(String steamId) {
        var response = SteamApiClient.callSteam(steamApiUrl, steamApiKey, steamId, restTemplate);
        registerNewSyncResponse(steamId, response.getResponse());
    }

    private void registerNewSyncResponse(String steamId, SteamOwnedGamesResponse responseFromStream) {
        if(responseFromStream == null)
        {
            return;
        }

        var event = EventData.builderAsJson( UUID.randomUUID(), Events.GAMES_SYNCED.getEventName(), new GamesSyncedEvent(steamId, responseFromStream.getGameCount(), responseFromStream.getGames())).build();
        var streamName ="game-stream";
        dbClient.appendToStream(streamName, AppendToStreamOptions.get(), event)
                .thenAccept(result ->
                        log.info("Event written successfully to stream {}", streamName)
                )
                .exceptionally(ex -> {
                    log.error("Failed to write event: {}", ex.getMessage());
                    return null;
                });
    }
}
