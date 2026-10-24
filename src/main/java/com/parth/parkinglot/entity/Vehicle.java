package com.parth.parkinglot.entity;

import com.parth.parkinglot.exception.InvalidVehicleException;

public class Vehicle {
    private final String registrationNumber;
    private final VehicleType type;

    public Vehicle(String registrationNumber, VehicleType type) {
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new InvalidVehicleException("Registration number is required");
        }
        if (type == null) throw new InvalidVehicleException("Vehicle type is required");
        this.registrationNumber = registrationNumber.trim().toUpperCase();
        this.type = type;
    }
    public String getRegistrationNumber() { return registrationNumber; }
    public VehicleType getType() { return type; }
}
