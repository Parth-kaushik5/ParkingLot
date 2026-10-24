package com.parth.parkinglot;

import com.parth.parkinglot.entity.*;
import com.parth.parkinglot.exception.ParkingLotException;
import com.parth.parkinglot.service.ParkingLotService;
import com.parth.parkinglot.strategy.allocation.BestFitAllocationStrategy;
import com.parth.parkinglot.strategy.fee.HourlyParkingFeeStrategy;
import com.parth.parkinglot.strategy.payment.CardPaymentStrategy;
import com.parth.parkinglot.strategy.payment.CashPaymentStrategy;
import com.parth.parkinglot.strategy.payment.PaymentStrategy;
import com.parth.parkinglot.strategy.payment.UpiPaymentStrategy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== SMART PARKING LOT SETUP ===");
        List<ParkingFloor> floors = createFloors(scanner);
        ParkingLotService parkingLot = new ParkingLotService(
                floors, new BestFitAllocationStrategy(), new HourlyParkingFeeStrategy());
        runMenu(scanner, parkingLot);
    }

    private static List<ParkingFloor> createFloors(Scanner scanner) {
        int floorCount = readNumber(scanner, "How many floors? ", 1, 20);
        List<ParkingFloor> floors = new ArrayList<>();
        for (int floorNumber = 1; floorNumber <= floorCount; floorNumber++) {
            System.out.println("Configure floor " + floorNumber + ":");
            List<ParkingSpot> spots = new ArrayList<>();
            int nextSpotNumber = 1;
            nextSpotNumber = addSpots(spots, nextSpotNumber, SpotType.MOTORCYCLE,
                    readNumber(scanner, "  Motorcycle spots: ", 0, 1000));
            nextSpotNumber = addSpots(spots, nextSpotNumber, SpotType.COMPACT,
                    readNumber(scanner, "  Compact spots: ", 0, 1000));
            nextSpotNumber = addSpots(spots, nextSpotNumber, SpotType.LARGE,
                    readNumber(scanner, "  Large spots: ", 0, 1000));
            addSpots(spots, nextSpotNumber, SpotType.BUS,
                    readNumber(scanner, "  Bus spots: ", 0, 1000));
            floors.add(new ParkingFloor(floorNumber, spots));
        }
        return floors;
    }

    private static int addSpots(List<ParkingSpot> spots, int firstNumber, SpotType type, int count) {
        for (int i = 0; i < count; i++) spots.add(new ParkingSpot(firstNumber + i, type));
        return firstNumber + count;
    }

    private static void runMenu(Scanner scanner, ParkingLotService parkingLot) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== PARKING LOT MENU ===");
            System.out.println("1. Show availability");
            System.out.println("2. Check in vehicle");
            System.out.println("3. Check out and pay");
            System.out.println("0. Exit");
            int choice = readNumber(scanner, "Choose an option: ", 0, 3);
            try {
                switch (choice) {
                    case 1:
                        System.out.println("Available spots by floor: " + parkingLot.getAvailability());
                        break;
                    case 2:
                        checkIn(scanner, parkingLot);
                        break;
                    case 3:
                        checkOut(scanner, parkingLot);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Parking lot closed.");
                        break;
                    default:
                        break;
                }
            } catch (ParkingLotException exception) {
                System.out.println("Could not complete request: " + exception.getMessage());
            }
        }
    }

    private static void checkIn(Scanner scanner, ParkingLotService parkingLot) {
        System.out.print("Vehicle registration: ");
        String registration = scanner.nextLine().trim();
        System.out.println("Vehicle type: 1. Motorcycle  2. Car  3. Bus");
        int typeChoice = readNumber(scanner, "Choose vehicle type: ", 1, 3);
        VehicleType type;
        switch (typeChoice) {
            case 1: type = VehicleType.MOTORCYCLE; break;
            case 2: type = VehicleType.CAR; break;
            default: type = VehicleType.BUS; break;
        }
        Vehicle vehicle = new Vehicle(registration, type);
        ParkingTicket ticket = parkingLot.checkIn(vehicle);
        printTicket(ticket);
    }

    private static void checkOut(Scanner scanner, ParkingLotService parkingLot) {
        System.out.print("Vehicle registration: ");
        String registration = scanner.nextLine().trim();
        System.out.println("Payment method: 1. Cash  2. Card  3. UPI");
        int paymentChoice = readNumber(scanner, "Choose payment method: ", 1, 3);
        PaymentStrategy paymentStrategy;
        switch (paymentChoice) {
            case 1: paymentStrategy = new CashPaymentStrategy(); break;
            case 2: paymentStrategy = new CardPaymentStrategy(); break;
            default: paymentStrategy = new UpiPaymentStrategy(); break;
        }
        ParkingReceipt receipt = parkingLot.checkOut(registration, paymentStrategy);
        printReceipt(receipt);
    }

    private static int readNumber(Scanner scanner, String prompt, int minimum, int maximum) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);
                if (number >= minimum && number <= maximum) return number;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a whole number from " + minimum + " to " + maximum + ".");
        }
    }

    private static void printTicket(ParkingTicket ticket) {
        System.out.println("--- PARKING TICKET ---");
        System.out.println("Ticket ID    : " + ticket.getId());
        System.out.println("Vehicle      : " + ticket.getVehicle().getRegistrationNumber() + " (" + ticket.getVehicle().getType() + ")");
        System.out.println("Floor / spot : " + ticket.getFloor().getNumber() + " / " + ticket.getSpot().getNumber());
        System.out.println("Spot type    : " + ticket.getSpot().getType());
        System.out.println("Entry time   : " + new Date(ticket.getEntryTimeMillis()));
    }

    private static void printReceipt(ParkingReceipt receipt) {
        System.out.println("--- PAYMENT RECEIPT ---");
        System.out.println("Ticket ID    : " + receipt.getTicket().getId());
        System.out.println("Exit time    : " + new Date(receipt.getExitTimeMillis()));
        System.out.println("Parking fee  : Rs. " + receipt.getFee());
        System.out.println("Payment      : " + receipt.getPayment().getMethod() + " - " + receipt.getPayment().getStatus());
        System.out.println("Transaction  : " + receipt.getPayment().getTransactionId());
    }
}
