package com.tbp.backend.external.steam;

import com.tbp.backend.dto.steam.SteamOwnedGamesResponseWrapper;


import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class SteamApiClient {

    private SteamApiClient() {}

    public static SteamOwnedGamesResponseWrapper callSteam(String apiUrl, String apiKey, String steamId, RestTemplate restTemplate)
    {
        URI steamApiUri = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("key", apiKey)
                .queryParam("steamid", steamId)
                .queryParam("format", "json")
                .queryParam("include_appinfo", true)
                .queryParam("include_played_free_games", true)
                .build()
                .toUri();

            return restTemplate.getForObject( steamApiUri, SteamOwnedGamesResponseWrapper.class);


    }
}
