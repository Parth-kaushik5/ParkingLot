package com.parth.parkinglot.strategy.allocation;

import com.parth.parkinglot.entity.ParkingFloor;
import com.parth.parkinglot.entity.ParkingSpot;

public class SpotAssignment {
    private final ParkingFloor floor;
    private final ParkingSpot spot;
    public SpotAssignment(ParkingFloor floor, ParkingSpot spot) { this.floor = floor; this.spot = spot; }
    public ParkingFloor getFloor() { return floor; }
    public ParkingSpot getSpot() { return spot; }
}
