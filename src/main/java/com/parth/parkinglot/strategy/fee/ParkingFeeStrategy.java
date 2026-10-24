package com.parth.parkinglot.strategy.fee;

import com.parth.parkinglot.entity.ParkingTicket;

public interface ParkingFeeStrategy {
    long calculateFee(ParkingTicket ticket, long exitTimeMillis);
}
