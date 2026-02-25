package com.example.core_routex.dto;

public class DistanceResponseDTO {

    private double distanceInKm;
    private String message;

    // Constructors
    public DistanceResponseDTO() {}

    public DistanceResponseDTO(double distanceInKm, String message) {
        this.distanceInKm = distanceInKm;
        this.message = message;
    }

    // Getters & Setters
    public double getDistanceInKm() { return distanceInKm; }
    public void setDistanceInKm(double distanceInKm) { this.distanceInKm = distanceInKm; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}