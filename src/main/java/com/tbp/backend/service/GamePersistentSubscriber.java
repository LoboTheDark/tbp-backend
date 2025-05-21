package com.tbp.backend.service;

import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBPersistentSubscriptionsClient;
import com.eventstore.dbclient.PersistentSubscription;
import com.eventstore.dbclient.PersistentSubscriptionListener;
import com.eventstore.dbclient.ReadStreamOptions;
import com.eventstore.dbclient.ResolvedEvent;
import com.eventstore.dbclient.SubscribePersistentSubscriptionOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbp.backend.datamodel.eventstore.Events;
import com.tbp.backend.datamodel.eventstore.GamesSyncedEvent;
import com.tbp.backend.datamodel.readmodel.GameReadModel;
import com.tbp.backend.datamodel.readmodel.GameReadRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class GamePersistentSubscriber {
    private final EventStoreDBPersistentSubscriptionsClient persistentClient;
    private final GameReadRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SteamImageService steamImageService;

    public GamePersistentSubscriber(EventStoreDBPersistentSubscriptionsClient persistentClient, GameReadRepository repository, SteamImageService steamImageService) {
        this.persistentClient = persistentClient;
        this.repository = repository;
        this.steamImageService = steamImageService;
    }

    @PostConstruct
    public void startListening() {
        log.info("Start event store subscription");
        var options = SubscribePersistentSubscriptionOptions.get().bufferSize(10);

        persistentClient.subscribeToStream(
                "game-stream",  // stream name
                "read-model",         // group name
                options,
                createNewListener()  // event handler

        ).whenComplete((ignored, error) -> {
            if (error != null) {
                log.error("Subscription failed: {}", error.getMessage());
            } else {
                log.error("Listening to persistent subscription");
            }
        });

    }

    private PersistentSubscriptionListener createNewListener()
    {
        return new PersistentSubscriptionListener() {
            @Override
            public void onEvent(PersistentSubscription subscription, int retryCount, ResolvedEvent event) {
                try {
                    String type = event.getOriginalEvent().getEventType();

                    if (Events.GAMES_SYNCED.getEventName().equals(type)) {
                        log.info("Caught event from event db");
                        handleGameSyncEvent(event);
                    }
                } catch (InterruptedException e) {
                        log.error("InterruptedException during process event {}", e.getMessage());
                        Thread.currentThread().interrupt();
                } catch (IOException e) {
                    log.error("IOException during process event {}", e.getMessage());
                }

    }
        };
    }

    private void handleGameSyncEvent(ResolvedEvent event) throws IOException, InterruptedException {
        byte[] json = event.getOriginalEvent().getEventData();
        var eventEntry = objectMapper.readValue(json, GamesSyncedEvent.class);

        for(var game: eventEntry.gamesList())
        {
            var existingGame = repository.findById(game.getAppid() + "");
            var gameImageBase64 = steamImageService.getScreenshotBase64(game.getAppid());
            var gameToChange = existingGame.orElseGet(() -> new GameReadModel(game.getAppid(), game.getName(), game.getPlaytimeForever()));
            gameToChange.setTitleImage(gameImageBase64);
            gameToChange.getSteamIds().add(eventEntry.steamId());
            repository.save(gameToChange);
            log.info("Saved game {}", game.getAppid());

            Thread.sleep(500);
        }
    }
}
