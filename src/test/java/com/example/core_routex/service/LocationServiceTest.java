package com.example.core_routex.service;

import com.example.core_routex.model.Location;
import com.example.core_routex.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    private LocationRepository locationRepository;
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        locationRepository = mock(LocationRepository.class); // Mock repo
        locationService = new LocationService(locationRepository);
    }

    @Test
    void testAddLocation() {
        Location loc = new Location();
        loc.setName("A");
        loc.setLatitude(12.9716);
        loc.setLongitude(77.5946);

        when(locationRepository.save(loc)).thenReturn(loc);

        Location saved = locationService.addLocation(loc);

        assertNotNull(saved);
        assertEquals("A", saved.getName());
        assertEquals(12.9716, saved.getLatitude());
        assertEquals(77.5946, saved.getLongitude());

        verify(locationRepository, times(1)).save(loc);
    }

    @Test
    void testGetLocationByName() {
        Location loc = new Location();
        loc.setName("B");

        when(locationRepository.findByName("B")).thenReturn(Optional.of(loc));

        Location fetched = locationService.getLocationByName("B");
        assertNotNull(fetched);
        assertEquals("B", fetched.getName());
    }
}