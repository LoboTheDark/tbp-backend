package com.tbp.backend.config;
import com.eventstore.dbclient.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventStoreConfig {

    @Bean
    public EventStoreDBClient eventStoreClient()  {
        EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow(
                "esdb://localhost:2113?tls=false"
        );
        return EventStoreDBClient.create(settings);
    }
}
