package com.parth.parkinglot.entity;

public enum VehicleType {
    MOTORCYCLE(1), CAR(2), BUS(4);
    private final int size;
    VehicleType(int size) { this.size = size; }
    public int getSize() { return size; }
}
