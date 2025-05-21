package com.tbp.backend.datamodel.eventstore;

import lombok.Getter;

public enum Events {
    GAMES_SYNCED("GamesSynced");

    @Getter
    private String eventName ="";
    Events(String eventName)
    {
        this.eventName = eventName;
    }
}
