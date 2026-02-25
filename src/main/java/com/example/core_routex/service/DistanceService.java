package com.example.core_routex.service;

import com.example.core_routex.dto.LocationDTO;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    // Calculate distance between two coordinates using Haversine formula
    public double calculateDistance(LocationDTO from, LocationDTO to) {

        double R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(to.getLatitude() - from.getLatitude());
        double lonDistance = Math.toRadians(to.getLongitude() - from.getLongitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(from.getLatitude())) * Math.cos(Math.toRadians(to.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // distance in km
    }
}