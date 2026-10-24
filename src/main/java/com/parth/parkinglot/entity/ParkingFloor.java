package com.parth.parkinglot.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingFloor {
    private final int number;
    private final List<ParkingSpot> spots;
    public ParkingFloor(int number, List<ParkingSpot> spots) {
        if (number < 1) throw new IllegalArgumentException("Floor number must be positive");
        this.number = number;
        this.spots = new ArrayList<>(spots);
    }
    public int getNumber() { return number; }
    public List<ParkingSpot> getSpots() { return Collections.unmodifiableList(spots); }
}
