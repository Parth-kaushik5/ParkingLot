package com.parth.parkinglot.exception;

public class VehicleAlreadyParkedException extends ParkingLotException {
    public VehicleAlreadyParkedException(String registration) {
        super("Vehicle is already parked: " + registration);
    }
}
