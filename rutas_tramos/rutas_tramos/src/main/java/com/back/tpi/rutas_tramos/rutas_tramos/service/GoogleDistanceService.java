package com.back.tpi.rutas_tramos.rutas_tramos.service;

import com.back.tpi.rutas_tramos.rutas_tramos.dto.DistanceResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class GoogleDistanceService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public DistanceResult getDistance(String origin, String destination) {

        try {
            String url = String.format(
                    "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&units=metric&key=%s",
                    URLEncoder.encode(origin, StandardCharsets.UTF_8),
                    URLEncoder.encode(destination, StandardCharsets.UTF_8),
                    apiKey
            );

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                throw new RuntimeException("Google devolvió respuesta nula");
            }

            // Nombres reales de origen y destino
            List<String> originAddresses = (List<String>) response.get("origin_addresses");
            List<String> destinationAddresses = (List<String>) response.get("destination_addresses");

            String origenNombre = originAddresses != null && !originAddresses.isEmpty()
                    ? originAddresses.get(0)
                    : "Origen desconocido";

            String destinoNombre = destinationAddresses != null && !destinationAddresses.isEmpty()
                    ? destinationAddresses.get(0)
                    : "Destino desconocido";

            // rows -> elements -> distance/duration
            List<Map<String, Object>> rows = (List<Map<String, Object>>) response.get("rows");
            if (rows == null || rows.isEmpty()) {
                throw new RuntimeException("Google no devolvió filas (rows)");
            }

            List<Map<String, Object>> elements = (List<Map<String, Object>>) rows.get(0).get("elements");
            if (elements == null || elements.isEmpty()) {
                throw new RuntimeException("Google no devolvió elementos en rows[0]");
            }

            Map<String, Object> elem = elements.get(0);

            Map<String, Object> distance = (Map<String, Object>) elem.get("distance");
            Map<String, Object> duration = (Map<String, Object>) elem.get("duration");

            if (distance == null || duration == null) {
                throw new RuntimeException("Google no devolvió distancia o duración");
            }

            int distanciaKm = ((Number) distance.get("value")).intValue() / 1000;
            String duracionTexto = (String) duration.get("text");
            int duracionMin = ((Number) duration.get("value")).intValue() / 60;

            return new DistanceResult(
                    origin,
                    destination,
                    origenNombre,
                    destinoNombre,
                    distanciaKm,
                    duracionTexto,
                    duracionMin
            );

        } catch (Exception e) {
            throw new RuntimeException("Error procesando respuesta de Google: " + e.getMessage(), e);
        }
    }
}

