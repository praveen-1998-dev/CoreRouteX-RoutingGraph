package com.example.core_routex.repository;

import com.example.core_routex.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    // Find a location by name (optional, for avoiding duplicates)
    Optional<Location> findByName(String name);
}