package com.example.core_routex.service;

import com.example.core_routex.dto.LocationDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RoutingClient {

    private final RestTemplate restTemplate;

    public RoutingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Send edge data to second microservice
    public void sendEdge(LocationDTO from, LocationDTO to, double distance) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("from", from);
        payload.put("to", to);
        payload.put("distance", distance);

        try {
            restTemplate.postForObject("http://localhost:8081/api/routing/edges", payload, String.class);
        } catch (Exception e) {
            // Log error, but don't crash the first microservice
            System.err.println("Failed to send edge to Routing Service: " + e.getMessage());
        }
    }
}