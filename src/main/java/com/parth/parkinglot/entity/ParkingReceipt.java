package com.parth.parkinglot.entity;

public class ParkingReceipt {
    private final ParkingTicket ticket;
    private final long exitTimeMillis;
    private final long fee;
    private final Payment payment;

    public ParkingReceipt(ParkingTicket ticket, long exitTimeMillis, long fee, Payment payment) {
        this.ticket = ticket;
        this.exitTimeMillis = exitTimeMillis;
        this.fee = fee;
        this.payment = payment;
    }
    public ParkingTicket getTicket() { return ticket; }
    public long getExitTimeMillis() { return exitTimeMillis; }
    public long getFee() { return fee; }
    public Payment getPayment() { return payment; }
}
