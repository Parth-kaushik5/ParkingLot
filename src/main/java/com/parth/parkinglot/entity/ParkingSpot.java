package com.parth.parkinglot.entity;

import com.parth.parkinglot.exception.InvalidParkingSpotException;
import com.parth.parkinglot.exception.SpotUnavailableException;

public class ParkingSpot {
    private final int number;
    private final SpotType type;
    private Vehicle parkedVehicle;

    public ParkingSpot(int number, SpotType type) {
        if (number < 1) throw new InvalidParkingSpotException("Spot number must be positive");
        if (type == null) throw new InvalidParkingSpotException("Spot type is required");
        this.number = number;
        this.type = type;
    }
    public int getNumber() { return number; }
    public SpotType getType() { return type; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }
    public boolean isAvailable() { return parkedVehicle == null; }
    public boolean canFit(Vehicle vehicle) { return isAvailable() && type.canFit(vehicle.getType()); }
    public void park(Vehicle vehicle) {
        if (vehicle == null || !canFit(vehicle)) throw new SpotUnavailableException("Spot is occupied or incompatible");
        parkedVehicle = vehicle;
    }
    public void vacate() {
        if (isAvailable()) throw new SpotUnavailableException("Spot is already empty");
        parkedVehicle = null;
    }
}
