# Parking Lot

A small in-memory parking lot application written in Core Java to practice object-oriented design and low-level design.

## Features

- Configure the number of floors and spot counts when the app starts.
- Park motorcycles, cars, and buses in compatible spots.
- Issue a parking ticket with the vehicle, assigned spot, floor, and entry time.
- Check a vehicle out, calculate its fee, process a payment, and free its spot.
- Show available spots by floor and spot type.
- Prevent two simultaneous check-ins from taking the same spot within this application process.

## Architecture overview

```text
                         +--------------------+
                         |    Main / Console  |
                         +---------+----------+
                                   |
                         +---------v----------+
                         |  ParkingLotService |
                         | check-in / checkout|
                         +----+----------+----+
                              |          |
                  +-----------v--+   +---v----------------+
                  | In-memory    |   | Strategy interfaces|
                  | floors/tickets|   | allocation / fee / |
                  +--------------+   | payment             |
                                     +---+---------+-------+
                                         |         |
                              +----------v-+    +--v----------------+
                              | Best-fit   |    | Hourly fee +      |
                              | allocation |    | Cash/Card/UPI pay |
                              +------------+    +-------------------+
```

`ParkingLotService` stores the lot layout in a `List<ParkingFloor>` and active tickets in a `Map<String, ParkingTicket>`, keyed by vehicle registration. Each spot holds its parked vehicle; availability is calculated from the spots.

## Patterns and design

- **Strategy:** `ParkingSpotAllocationStrategy`, `ParkingFeeStrategy`, and `PaymentStrategy` make allocation, pricing, and payment rules swappable.
- **Dependency injection:** `Main` creates the strategies and passes them to `ParkingLotService`.
- **Single responsibility:** entities hold parking data, strategies apply one rule, the service coordinates operations, and `Main` runs the console application.
- **Exception handling:** named parking exceptions are caught at the console boundary and shown as readable errors.
- **Concurrency:** synchronized service methods make each operation atomic inside one running application. A multi-server system would need a shared database lock.

## Design notes

- Spot sizes are ranked: motorcycle (1), compact (2), large (3), bus (4). A spot fits a vehicle if its capacity is at least the vehicle's size. A bus uses a dedicated bus spot.
- Best-fit allocation scans the floors and spots, chooses the smallest compatible available spot, then breaks ties by floor and spot number.
- Parking times use `System.currentTimeMillis()`. The fee strategy subtracts entry time from exit time and rounds up to each started hour.
- Hourly rates: motorcycle Rs. 20, car Rs. 50, bus Rs. 100. A stay shorter than one hour costs one hour.
- Cash, card, and UPI payment strategies simulate success; no real payment gateway is connected.
- Data stays in memory and resets when the application exits.

## Run in IntelliJ

Open the Maven project with JDK 8 or higher and run `Main.main()` from `src/main/java/com/parth/parkinglot/Main.java`.

When the app starts, enter the floor count and the motorcycle, compact, large, and bus spot counts for each floor. Use the menu to show availability, check vehicles in, check vehicles out, and exit.
