package com.tbp.backend.dto.steam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SteamGameDto {
    private int appid;
    private String name;

    @JsonProperty("playtime_forever")
    private int playtimeForever; // Played time in minutes

}
