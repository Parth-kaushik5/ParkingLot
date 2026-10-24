package com.parth.parkinglot.exception;

public class NoAvailableSpotException extends ParkingLotException {
    public NoAvailableSpotException(String vehicleType) {
        super("No compatible parking spot available for: " + vehicleType);
    }
}
