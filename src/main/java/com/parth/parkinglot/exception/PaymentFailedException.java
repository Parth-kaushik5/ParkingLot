package com.parth.parkinglot.exception;

public class PaymentFailedException extends ParkingLotException {
    public PaymentFailedException(String ticketId) {
        super("Payment failed for ticket " + ticketId + "; vehicle remains parked");
    }
}
