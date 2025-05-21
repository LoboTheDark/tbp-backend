package com.tbp.backend.dto.steam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class SteamOwnedGamesResponse {
    @JsonProperty("game_count") // Match the JSON key
    private int gameCount;

    private List<SteamGameDto> games; // List of SteamGame objects
}
