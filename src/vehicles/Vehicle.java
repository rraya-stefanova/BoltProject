package vehicles;

import java.time.LocalDateTime;
import java.util.Objects;
import roles.Driver;
import exceptions.VehicleAlreadyRentedException;
import exceptions.VehicleNotRentedException;
import exceptions.InvalidRentingPeriodException;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;


public abstract class Vehicle {

    protected final long MAX_HOURS = 1000000000;

    private String id;
    private String model;

    private boolean rented;
    private Driver rentedBy;
    private LocalDateTime rentalBegin;
    private LocalDateTime rentalEnd;

    private long calculateTotalRentTime(LocalDateTime end){
        return HOURS.between(rentalBegin, rentalEnd);
    }
    public Vehicle(String id, String model) {
        this.id = id;
        this.model = model;

        this.rented = false;
        this.rentedBy = null;
        this.rentalBegin = null;
    }

    public boolean isAvailable() {
        return !rented;
    }

    public Driver getRentedBy() {
        return rentedBy;
    }

    public LocalDateTime getRentedOn() {
        return rentalBegin;
    }
    public LocalDateTime getRentalEnd() {
        return rentalEnd;
    }

    public abstract long minRentalTimeInHours();

    public abstract long maxRentalTimeInHours();

    /**
     * Simulates rental of the vehicle. The vehicle now is considered rented by the provided driver and the start of the rental is the provided date.
     * @param driver the driver that wants to rent the vehicle.
     * @param startRentTime the start time of the rent
     * @throws VehicleAlreadyRentedException in case the vehicle is already rented by someone else or by the same driver.
     */

    public void rent(Driver driver, LocalDateTime startRentTime) {

        try {
            if (this.rented) {
                throw new VehicleAlreadyRentedException();
            }
            this.rented = true;
            this.rentedBy = driver;
            this.rentalBegin = startRentTime;

        } catch(VehicleAlreadyRentedException e) {
            System.err.println("Exception is: " + e.getClass().getName());
            throw new IllegalArgumentException("Invalid operation: " + e.getMessage(),e);
        }
    }
    /**
     * Simulates end of rental for the vehicle - it is no longer rented by a driver.
     * @param rentalEnd time of end of rental
     * @throws IllegalArgumentException in case @rentalEnd is null
     * @throws VehicleNotRentedException in case the vehicle is not rented at all
     * @throws InvalidRentingPeriodException in case the rentalEnd is before the currently noted start date of rental or
     * in case the Vehicle does not allow the passed period for rental, e.g. Caravans must be rented for at least a day
     * and the driver tries to return them after an hour.
     */
    public void returnBack(LocalDateTime rentalEnd) throws InvalidRentingPeriodException {
        try {
            if(rentalEnd == null){
                throw new IllegalArgumentException();
            }
            if (!this.rented) {
                throw new VehicleNotRentedException();
            }
            if(rentalEnd.isBefore(rentalBegin) || calculateTotalRentTime(rentalEnd) < minRentalTimeInHours()){
                throw new InvalidRentingPeriodException();
            }
            this.rented = false;
            this.rentalEnd = rentalEnd;

        } catch(IllegalArgumentException e){
            System.err.println("Exception is: " + e.getClass().getName());
        } catch(VehicleNotRentedException | InvalidRentingPeriodException e) {
            System.err.println("Exception is: " + e.getClass().getName());
            throw new IllegalArgumentException("Invalid operation: " + e.getMessage(), e);
        }
    }


    /**
     * Used to calculate potential rental price without the vehicle to be rented.
     * The calculation is based on the type of the Vehicle (Car/Caravan/Bicycle).
     *
     * @param startOfRent the beginning of the rental
     * @param endOfRent the end of the rental
     * @return potential price for rent
     * @throws InvalidRentingPeriodException in case the vehicle cannot be rented for that period of time or
     * the period is not valid (end date is before start date)
     */
    public abstract double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException;
}
