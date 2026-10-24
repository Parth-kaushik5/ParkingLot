package com.parth.parkinglot.strategy.allocation;

import com.parth.parkinglot.entity.ParkingFloor;
import com.parth.parkinglot.entity.Vehicle;
import java.util.List;

public interface ParkingSpotAllocationStrategy {
    // Returns null when no available spot can fit the vehicle.
    SpotAssignment findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}
