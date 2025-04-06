package rentalservices;

import exceptions.*;
import roles.Driver;
import vehicles.*;

import java.time.LocalDateTime;


public class RentalService {

    /**
     * Simulates renting of the vehicle. Makes all required validations and then the provided driver "rents" the provided
     * vehicle with start time @startOfRent
     * @throws IllegalArgumentException if any of the passed arguments are null
     * @throws VehicleAlreadyRentedException in case the provided vehicle is already rented
     * @param driver the designated driver of the vehicle
     * @param vehicle the chosen vehicle to be rented
     * @param startOfRent the start time of the rental
     */
    public void rentVehicle(Driver driver, Vehicle vehicle, LocalDateTime startOfRent) {
        try{
            if(driver == null || vehicle == null || startOfRent == null){
                throw new IllegalArgumentException("Given argument with null value.");
            }
            if(!vehicle.isAvailable()){
                throw new VehicleAlreadyRentedException();
            }
            vehicle.rent(driver,startOfRent);
        } catch (IllegalArgumentException e){
            System.err.println("Exception is: " + e.getMessage());
        } catch (VehicleAlreadyRentedException e){
            System.err.println("Exception is: " + e.getMessage());
            throw new IllegalArgumentException("Invalid operation." + e);
        }

    }


    /**
     * This method simulates rental return - it includes validation of the arguments that throw the listed exceptions
     * in case of errors. The method returns the expected total price for the rental - price for the vehicle plus
     * additional tax for the driver, if it is applicable
     * @param vehicle the rented vehicle
     * @param endOfRent the end time of the rental
     * @return price for the rental
     * @throws IllegalArgumentException in case @endOfRent or @vehicle is null
     * @throws VehicleNotRentedException in case the vehicle is not rented at all
     * @throws InvalidRentingPeriodException in case the endOfRent is before the start of rental, or the vehicle
     * does not allow the passed period for rental, e.g. Caravans must be rented for at least a day.
     */

    public double returnVehicle(Vehicle vehicle, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        double totalRentPrice = 0;

        try{
            if(vehicle == null || endOfRent == null){
                throw new IllegalArgumentException("Given argument with null value.");
            }
            if(vehicle.isAvailable()){
                throw new VehicleNotRentedException();
            }
            if(endOfRent.isBefore(vehicle.getRentedOn())){
                throw new InvalidRentingPeriodException();
            }
            vehicle.returnBack(endOfRent);
        } catch (IllegalArgumentException e){
            System.err.println("Exception is: " + e.getMessage());
        } catch (VehicleAlreadyRentedException | InvalidRentingPeriodException e){
            System.err.println("Exception is: " + e.getMessage());
            throw new IllegalArgumentException("Invalid operation." + e);
        } finally{

            totalRentPrice += vehicle.calculateRentalPrice(vehicle.getRentedOn(),endOfRent);
            totalRentPrice += vehicle.getRentedBy().calculateTax();
            return totalRentPrice;

        }
    }


}