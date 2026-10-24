package com.parth.parkinglot.service;

import com.parth.parkinglot.entity.*;
import com.parth.parkinglot.exception.InvalidParkingRequestException;
import com.parth.parkinglot.exception.InvalidVehicleException;
import com.parth.parkinglot.exception.NoAvailableSpotException;
import com.parth.parkinglot.exception.ParkingTicketNotFoundException;
import com.parth.parkinglot.exception.PaymentFailedException;
import com.parth.parkinglot.exception.VehicleAlreadyParkedException;
import com.parth.parkinglot.strategy.allocation.ParkingSpotAllocationStrategy;
import com.parth.parkinglot.strategy.allocation.SpotAssignment;
import com.parth.parkinglot.strategy.fee.ParkingFeeStrategy;
import com.parth.parkinglot.strategy.payment.PaymentStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class ParkingLotService {
    private final List<ParkingFloor> floors;
    private final ParkingSpotAllocationStrategy allocationStrategy;
    private final ParkingFeeStrategy feeStrategy;
    private final Map<String, ParkingTicket> activeTickets = new HashMap<>();
    private int nextTicketNumber = 1;

    public ParkingLotService(List<ParkingFloor> floors, ParkingSpotAllocationStrategy allocationStrategy,
                             ParkingFeeStrategy feeStrategy) {
        this.floors = new ArrayList<>(floors);
        this.allocationStrategy = Objects.requireNonNull(allocationStrategy);
        this.feeStrategy = Objects.requireNonNull(feeStrategy);
    }

    public synchronized ParkingTicket checkIn(Vehicle vehicle) {
        if (vehicle == null) throw new InvalidVehicleException("Vehicle is required");
        String registration = vehicle.getRegistrationNumber();
        if (activeTickets.containsKey(registration)) throw new VehicleAlreadyParkedException(registration);
        SpotAssignment assignment = allocationStrategy.findSpot(floors, vehicle);
        if (assignment == null) throw new NoAvailableSpotException(vehicle.getType().name());
        assignment.getSpot().park(vehicle);
        ParkingTicket ticket = new ParkingTicket("T-" + nextTicketNumber++, vehicle,
                assignment.getFloor(), assignment.getSpot(), System.currentTimeMillis());
        activeTickets.put(registration, ticket);
        return ticket;
    }

    public synchronized ParkingReceipt checkOut(String registrationNumber, PaymentStrategy paymentStrategy) {
        if (paymentStrategy == null) throw new InvalidParkingRequestException("Payment method is required");
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new InvalidParkingRequestException("Vehicle registration is required for checkout");
        }
        String registration = registrationNumber.trim().toUpperCase();
        ParkingTicket ticket = activeTickets.get(registration);
        if (ticket == null) throw new ParkingTicketNotFoundException(registration);

        long exitTimeMillis = System.currentTimeMillis();
        long fee = feeStrategy.calculateFee(ticket, exitTimeMillis);
        Payment payment = paymentStrategy.pay(ticket.getId(), fee);
        if (payment == null || payment.getStatus() != PaymentStatus.SUCCESSFUL) {
            throw new PaymentFailedException(ticket.getId());
        }
        ticket.getSpot().vacate();
        activeTickets.remove(registration);
        return new ParkingReceipt(ticket, exitTimeMillis, fee, payment);
    }

    public synchronized Map<Integer, Map<SpotType, Long>> getAvailability() {
        Map<Integer, Map<SpotType, Long>> result = new TreeMap<>();
        for (ParkingFloor floor : floors) {
            Map<SpotType, Long> counts = new EnumMap<>(SpotType.class);
            for (ParkingSpot spot : floor.getSpots()) {
                if (spot.isAvailable()) counts.put(spot.getType(), counts.getOrDefault(spot.getType(), 0L) + 1);
                else if (!counts.containsKey(spot.getType())) counts.put(spot.getType(), 0L);
            }
            result.put(floor.getNumber(), Collections.unmodifiableMap(counts));
        }
        return Collections.unmodifiableMap(result);
    }
}
