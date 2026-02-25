package com.example.core_routex.controller;

import com.example.core_routex.model.Location;
import com.example.core_routex.service.LocationService;
import com.example.core_routex.service.RoutingClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
public class CoreRouteXController {

    private final LocationService locationService;
    private final RoutingClientService routingClientService;
    private static final Logger logger = LoggerFactory.getLogger(CoreRouteXController.class);

    public CoreRouteXController(LocationService locationService,
                                RoutingClientService routingClientService) {
        this.locationService = locationService;
        this.routingClientService = routingClientService;
    }

    // ---------- 1️⃣ Add Location ----------
    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        logger.info("Adding location: {} ({}, {})", location.getName(), location.getLatitude(), location.getLongitude());
        Location saved = locationService.addLocation(location);
        return ResponseEntity.ok(saved);
    }

    // ---------- 2️⃣ Get All Locations ----------
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        logger.info("Fetching all locations");
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    // ---------- 3️⃣ Calculate Distance (Haversine) ----------
    @PostMapping("/distance")
    public ResponseEntity<Double> calculateDistance(@RequestParam Double fromLatitude,
                                                    @RequestParam Double fromLongitude,
                                                    @RequestParam Double toLatitude,
                                                    @RequestParam Double toLongitude) {
        double distance = haversine(fromLatitude, fromLongitude, toLatitude, toLongitude);
        logger.info("Distance calculated: {} km", distance);
        return ResponseEntity.ok(distance);
    }

    // Haversine formula
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    // ---------- 4️⃣ Calculate Distance by Location Names ----------
    @PostMapping("/distance-by-name")
    public ResponseEntity<Double> calculateDistanceByName(@RequestParam String from,
                                                          @RequestParam String to) {
        Location fromLoc = locationService.getLocationByName(from);
        Location toLoc = locationService.getLocationByName(to);

        if (fromLoc == null || toLoc == null) {
            return ResponseEntity.badRequest().body(null);
        }

        double distance = haversine(fromLoc.getLatitude(), fromLoc.getLongitude(),
                toLoc.getLatitude(), toLoc.getLongitude());
        logger.info("Distance calculated from '{}' to '{}' = {} km", from, to, distance);
        return ResponseEntity.ok(distance);
    }

    // ---------- 5️⃣ Send Edge to Routing-Graph ----------
    @PostMapping("/send-edge")
    public ResponseEntity<String> sendEdge(@RequestParam String from,
                                           @RequestParam String to,
                                           @RequestParam Double distance) {
        routingClientService.sendEdgeToRoutingGraph(from, to, distance);
        logger.info("Edge sent from {} to {} = {} km", from, to, distance);
        return ResponseEntity.ok("Edge sent to routing-graph");
    }
    // ---------- 7️⃣ Get all edges from Routing-Graph ----------
    @GetMapping("/edges")
    public ResponseEntity<Object> getAllEdgesFromCore() {
        Object edges = routingClientService.getAllEdgesFromRoutingGraph();
        logger.info("Returning all edges from routing-graph");
        return ResponseEntity.ok(edges);
    }

    // ---------- 6️⃣ Get Shortest Distance from Routing-Graph ----------
    @GetMapping("/shortest-distance")
    public ResponseEntity<Map<String, Object>> getShortestDistanceFromCore(
            @RequestParam String from,
            @RequestParam String to) {
        Map<String, Object> response = routingClientService.getShortestDistanceFromRoutingGraph(from, to);
        logger.info("Shortest distance requested from '{}' to '{}'", from, to);
        return ResponseEntity.ok(response);
    }
}