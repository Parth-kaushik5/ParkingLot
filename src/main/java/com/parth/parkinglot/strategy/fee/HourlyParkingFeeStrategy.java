package com.parth.parkinglot.strategy.fee;

import com.parth.parkinglot.entity.ParkingTicket;
import com.parth.parkinglot.entity.VehicleType;
import com.parth.parkinglot.exception.InvalidParkingRequestException;

/** Charges per started hour; rates are rupees per hour. */
public class HourlyParkingFeeStrategy implements ParkingFeeStrategy {
    private static final long MILLIS_PER_HOUR = 60L * 60L * 1000L;

    @Override
    public long calculateFee(ParkingTicket ticket, long exitTimeMillis) {
        long durationMillis = exitTimeMillis - ticket.getEntryTimeMillis();
        if (durationMillis < 0) throw new InvalidParkingRequestException("Exit cannot be before entry");
        long hours = Math.max(1, (durationMillis + MILLIS_PER_HOUR - 1) / MILLIS_PER_HOUR);
        return hours * getHourlyRate(ticket.getVehicle().getType());
    }

    private long getHourlyRate(VehicleType type) {
        switch (type) {
            case MOTORCYCLE: return 20;
            case CAR: return 50;
            case BUS: return 100;
            default: throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}

