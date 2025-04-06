package vehicles;

import exceptions.InvalidRentingPeriodException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Bicycle extends Vehicle {

    private double pricePerDay;
    private double pricePerHour;

    public Bicycle(String id, String model, double pricePerDay, double pricePerHour) {
        super(id, model);
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    @Override
    public long minRentalTimeInHours() {
        return 1;
    }

    @Override
    public long maxRentalTimeInHours() {
        return 24 * 7;
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

            days -= (long) Math.floor(days / 7) * 7;

            hours -= (long) Math.floor(hours / 24) * 24;

            totalRentingPrice = days * pricePerDay + hours * pricePerHour;

        } catch (InvalidRentingPeriodException e) {
            throw new IllegalArgumentException("Invald renting period.", e);
        } finally {
            return totalRentingPrice;
        }

    }

}
