package vehicles;

import exceptions.InvalidRentingPeriodException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Car extends Vehicle {


    private FuelType fuelType;
    private int numberOfSeats;
    private double pricePerWeek;
    private double pricePerDay;
    private double pricePerHour;
    private final double priceForSeat = 5;

    public Car(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model);

        if (id == null || model == null) {
            throw new IllegalArgumentException("Invalid engine parameters.");
        }
        if (numberOfSeats < 0) {
            throw new IllegalArgumentException("Invalid number of seats.");
        }

        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.pricePerWeek = pricePerWeek;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;

    }

    @Override
    public long minRentalTimeInHours() {
        return 1;
    }

    @Override
    public long maxRentalTimeInHours() {
        return MAX_HOURS;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {

        double totalRentingPrice = 0;
        try {
            if (endOfRent.isBefore(startOfRent)) {
                throw new InvalidRentingPeriodException();
            }

            long hours = ChronoUnit.HOURS.between(startOfRent, endOfRent);
            long days = hours / 24;
            long weeks = days / 7;

            days -= (long) Math.floor(days / 7) * 7;

            hours -= (long) Math.floor(hours / 24) * 24;

            totalRentingPrice = days * pricePerDay + hours * pricePerHour + weeks * pricePerWeek + (numberOfSeats * priceForSeat) + fuelType.getTax() * days;

        } catch (InvalidRentingPeriodException e) {
            throw new IllegalArgumentException("Invald renting period.", e);
        } finally {
            return totalRentingPrice;
        }

    }
}

