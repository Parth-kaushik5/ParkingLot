package com.parth.parkinglot.entity;

public class ParkingTicket {
    private final String id;
    private final Vehicle vehicle;
    private final ParkingFloor floor;
    private final ParkingSpot spot;
    private final long entryTimeMillis;

    public ParkingTicket(String id, Vehicle vehicle, ParkingFloor floor, ParkingSpot spot, long entryTimeMillis) {
        this.id = id;
        this.vehicle = vehicle;
        this.floor = floor;
        this.spot = spot;
        this.entryTimeMillis = entryTimeMillis;
    }
    public String getId() { return id; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingFloor getFloor() { return floor; }
    public ParkingSpot getSpot() { return spot; }
    public long getEntryTimeMillis() { return entryTimeMillis; }
}
