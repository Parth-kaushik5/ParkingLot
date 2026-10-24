package com.parth.parkinglot.exception;

/** Base unchecked exception for parking-lot business errors. */
public class ParkingLotException extends RuntimeException {
    public ParkingLotException(String message) { super(message); }
}
