package com.tbp.backend.datamodel.eventstore;

import com.tbp.backend.dto.steam.SteamGameDto;

import java.util.List;

public record GamesSyncedEvent(String steamId, int gamesCount, List<SteamGameDto> gamesList) {}
