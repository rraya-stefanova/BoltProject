package exceptions;

public class VehicleAlreadyRentedException extends RuntimeException {

    public VehicleAlreadyRentedException() {
        super("Vehicle is already rented");
    }
    public VehicleAlreadyRentedException(Throwable cause) {
        super("Vehicle is already rented.", cause);
    }
}