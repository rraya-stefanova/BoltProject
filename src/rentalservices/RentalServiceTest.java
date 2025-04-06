package rentalservices;

import java.time.LocalDateTime;

import roles.*;
import vehicles.*;
import exceptions.*;

public class RentalServiceTest {

    public static void main(String[] args) {
        RentalService rentalService = new RentalService();
        LocalDateTime rentStart = LocalDateTime.of(2024, 10, 10, 0, 0, 0);
        Driver experiencedDriver = new Driver(AgeGroup.EXPERIENCED);

        Vehicle electricCar = new Car("1", "Tesla Model 3", FuelType.ELECTRICITY, 4, 1000, 150, 10);
        rentalService.rentVehicle(experiencedDriver, electricCar, rentStart);

        try {
            double priceToPay = rentalService.returnVehicle(electricCar, rentStart.plusDays(5)); // 770.0
            System.out.println("Price to pay is: " + priceToPay);
        } catch (InvalidRentingPeriodException e) {
            System.err.println(e.getMessage());
        }


        Vehicle dieselCar = new Car("2", "Toyota Auris", FuelType.DIESEL, 4, 500, 80, 5);
        rentalService.rentVehicle(experiencedDriver, dieselCar, rentStart);
        try {
            double priceToPay = rentalService.returnVehicle(dieselCar, rentStart.plusDays(5)); // 80*5 + 3*5 + 4*5 = 435.0
            System.out.println("Price to pay is: " + priceToPay);
        } catch (InvalidRentingPeriodException e) {
            System.err.println(e.getMessage());
        }

    }
}