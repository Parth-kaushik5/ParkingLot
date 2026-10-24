package com.parth.parkinglot.entity;

public enum SpotType {
    MOTORCYCLE(1), COMPACT(2), LARGE(3), BUS(4);
    private final int capacity;
    SpotType(int capacity) { this.capacity = capacity; }
    public int getCapacity() { return capacity; }
    public boolean canFit(VehicleType vehicleType) { return capacity >= vehicleType.getSize(); }
}
