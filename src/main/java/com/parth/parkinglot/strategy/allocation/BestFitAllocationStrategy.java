package com.parth.parkinglot.strategy.allocation;

import com.parth.parkinglot.entity.ParkingFloor;
import com.parth.parkinglot.entity.ParkingSpot;
import com.parth.parkinglot.entity.Vehicle;
import java.util.List;

/** Picks the smallest compatible spot, then the lowest floor and spot number. */
public class BestFitAllocationStrategy implements ParkingSpotAllocationStrategy {
    @Override
    public SpotAssignment findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        SpotAssignment best = null;
        for (ParkingFloor floor : floors) {
            for (ParkingSpot spot : floor.getSpots()) {
                if (!spot.canFit(vehicle)) continue;
                if (best == null || isBetter(spot, floor, best)) {
                    best = new SpotAssignment(floor, spot);
                }
            }
        }
        return best;
    }

    private boolean isBetter(ParkingSpot candidate, ParkingFloor floor, SpotAssignment current) {
        int candidateSize = candidate.getType().getCapacity();
        int currentSize = current.getSpot().getType().getCapacity();
        if (candidateSize != currentSize) return candidateSize < currentSize;
        if (floor.getNumber() != current.getFloor().getNumber()) return floor.getNumber() < current.getFloor().getNumber();
        return candidate.getNumber() < current.getSpot().getNumber();
    }
}
