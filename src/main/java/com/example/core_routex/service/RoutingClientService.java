package com.example.core_routex.service;

import com.example.core_routex.dto.EdgeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RoutingClientService {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RoutingClientService.class);

    // URL of second microservice
    private static final String ROUTING_GRAPH_URL = "http://localhost:8081/api/edges";

    public RoutingClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ---------- 1️⃣ Send edge to Routing-Graph ----------
    public void sendEdgeToRoutingGraph(String from, String to, Double distanceInKm) {
        EdgeDTO edgeDTO = new EdgeDTO(from, to, distanceInKm);
        try {
            ResponseEntity<EdgeDTO> response = restTemplate.postForEntity(ROUTING_GRAPH_URL, edgeDTO, EdgeDTO.class);
            logger.info("Edge sent to routing-graph: {} -> {} = {} km, Response status: {}",
                    from, to, distanceInKm, response.getStatusCode());
        } catch (Exception e) {
            logger.error("Failed to send edge to routing-graph", e);
        }
    }

    // ---------- 2️⃣ Get shortest distance from Routing-Graph ----------
    public Map<String, Object> getShortestDistanceFromRoutingGraph(String from, String to) {
        String url = String.format("%s/shortest-distance?from=%s&to=%s", ROUTING_GRAPH_URL, from, to);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            logger.info("Shortest distance fetched from routing-graph: {} -> {}", from, to);
            return response;
        } catch (Exception e) {
            logger.error("Failed to fetch shortest distance from routing-graph", e);
            return Map.of("error", "Unable to fetch shortest distance");
        }
    }
    public Object getAllEdgesFromRoutingGraph() {
        try {
            Object response = restTemplate.getForObject(ROUTING_GRAPH_URL, Object.class);
            logger.info("Fetched all edges from routing-graph");
            return response;
        } catch (Exception e) {
            logger.error("Failed to fetch edges from routing-graph", e);
            return Map.of("error", "Unable to fetch edges");
        }
    }
}