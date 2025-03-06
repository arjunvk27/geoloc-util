package com.example.geolocutil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GeoLocationService {
    // API key provided for the exercise
    private static final String API_KEY = "f897a99d971b5eef57be6fafa0d83239";
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public GeoLocationService() {
        this.httpClient = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    /**
     * Determines the type of the input and retrieves the GeoLocation.
     * - A 5-digit input is treated as a zip code.
     * - An input containing a comma is treated as a "city, state" query.
     */
    public GeoLocation getLocation(String locationInput) throws IOException, InterruptedException {
        locationInput = locationInput.trim();
        if (locationInput.matches("\\d{5}")) {
            return getLocationByZip(locationInput);
        } else if (locationInput.contains(",")) {
            return getLocationByCityState(locationInput);
        } else {
            throw new IllegalArgumentException("Invalid location format: " + locationInput);
        }
    }

    private GeoLocation getLocationByZip(String zip) throws IOException, InterruptedException {
        String url = String.format("http://api.openweathermap.org/geo/1.0/zip?zip=%s,US&appid=%s",
                zip, API_KEY);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200) {
            throw new RuntimeException("API call failed with status code: " + response.statusCode());
        }
        return mapper.readValue(response.body(), GeoLocation.class);
    }

    private GeoLocation getLocationByCityState(String cityState) throws IOException, InterruptedException {
        String[] parts = cityState.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid city,state format: " + cityState);
        }
        String city = parts[0].trim();
        String state = parts[1].trim();
        String query = String.format("%s,%s,US", city, state);
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s",
                URLEncoder.encode(query, StandardCharsets.UTF_8), API_KEY);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200) {
            throw new RuntimeException("API call failed with status code: " + response.statusCode());
        }
        List<GeoLocation> locations = mapper.readValue(response.body(), new TypeReference<List<GeoLocation>>(){});
        if(locations.isEmpty()){
            throw new RuntimeException("No results found for location: " + cityState);
        }
        return locations.get(0);
    }
}
