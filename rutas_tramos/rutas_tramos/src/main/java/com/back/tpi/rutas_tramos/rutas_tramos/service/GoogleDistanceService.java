package com.back.tpi.rutas_tramos.rutas_tramos.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.back.tpi.rutas_tramos.rutas_tramos.dto.DistanceResponse;

@Service
public class GoogleDistanceService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public DistanceResponse getDistance(String origin, String destination) {
        String url = String.format(
            "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&units=metric&key=%s",
            origin, destination, apiKey
        );

        ResponseEntity<DistanceResponse> response =
            restTemplate.getForEntity(url, DistanceResponse.class);

        return response.getBody();
    }
}
