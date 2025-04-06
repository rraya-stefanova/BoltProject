package exceptions;

public class VehicleNotRentedException extends RuntimeException {

    public VehicleNotRentedException() {
        super("Vehicle is not currently rented.");
    }
    public VehicleNotRentedException(Throwable cause) {
        super("Vehicle is not currently rented.", cause);
    }
}