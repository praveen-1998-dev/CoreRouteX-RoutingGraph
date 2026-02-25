package com.example.core_routex.dto;

import jakarta.validation.constraints.NotNull;

public class DistanceRequestDTO {

    @NotNull(message = "From location is required")
    private LocationDTO from;

    @NotNull(message = "To location is required")
    private LocationDTO to;

    // Constructors
    public DistanceRequestDTO() {}

    public DistanceRequestDTO(LocationDTO from, LocationDTO to) {
        this.from = from;
        this.to = to;
    }

    // Getters & Setters
    public LocationDTO getFrom() { return from; }
    public void setFrom(LocationDTO from) { this.from = from; }

    public LocationDTO getTo() { return to; }
    public void setTo(LocationDTO to) { this.to = to; }
}