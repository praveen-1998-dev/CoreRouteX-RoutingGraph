package com.example.core_routex.service;

import com.example.core_routex.model.Location;
import com.example.core_routex.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Add a new location
    public Location addLocation(Location location) {
        // Optional: Check if name already exists
        Optional<Location> existing = locationRepository.findByName(location.getName());
        if (existing.isPresent()) {
            return existing.get(); // return existing instead of duplicate
        }
        return locationRepository.save(location);
    }
    public Location getLocationByName(String name) {
        return locationRepository.findByName(name).orElse(null);
    }

    // Get a single location by ID
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    // Get all locations
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}