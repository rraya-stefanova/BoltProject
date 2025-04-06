package vehicles;

import exceptions.InvalidRentingPeriodException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Caravan extends Vehicle {

    private FuelType fuelType;
    private int numberOfSeats;
    private int numberOfBeds;
    private double pricePerWeek;
    private double pricePerDay;
    private double pricePerHour;
    private final double priceForSeat = 5;
    private final double priceForBed = 10;

    public Caravan(String id, String model, FuelType fuelType, int numberOfSeats, int numberOfBeds, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model);
        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.pricePerWeek = pricePerWeek;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
        this.numberOfBeds = numberOfBeds;
    }

    @Override
    public long minRentalTimeInHours() {
        return 24;
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


            totalRentingPrice = days * pricePerDay + hours * pricePerHour + weeks * pricePerWeek + (numberOfSeats * priceForSeat) + (numberOfBeds * priceForBed);
            totalRentingPrice += fuelType.getTax() * days;

        } catch (InvalidRentingPeriodException e) {
            throw new IllegalArgumentException("Invald renting period.", e);
        } finally {
            return totalRentingPrice;
        }

    }
}
