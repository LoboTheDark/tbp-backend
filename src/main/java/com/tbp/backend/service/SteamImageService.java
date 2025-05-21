package com.tbp.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
public class SteamImageService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public String getScreenshotBase64(int appId) throws IOException, InterruptedException {
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;

        String json = restTemplate.getForObject(url, String.class);
        JsonNode root = objectMapper.readTree(json);
        JsonNode data = root.path(String.valueOf(appId)).path("data");

        var resultImage = "";
        if (data.has("screenshots")) {
            for (JsonNode screenshot : data.get("screenshots")) {
                String imageUrl = screenshot.get("path_full").asText();
                resultImage = downloadAndConvertToBase64(imageUrl);
                if(resultImage != null){
                    break;
                }
            }
        }

        return resultImage;
    }

    private String downloadAndConvertToBase64(String imageUrl) throws IOException, InterruptedException {
        HttpResponse<byte[]> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        }

        return Base64.getEncoder().encodeToString(response.body());
    }
}
