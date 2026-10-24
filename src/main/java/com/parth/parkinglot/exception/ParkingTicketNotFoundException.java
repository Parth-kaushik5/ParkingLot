package com.parth.parkinglot.exception;

public class ParkingTicketNotFoundException extends ParkingLotException {
    public ParkingTicketNotFoundException(String registration) {
        super("No active parking ticket for vehicle: " + registration);
    }
}
