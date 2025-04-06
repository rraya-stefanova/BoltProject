package exceptions;

public class InvalidRentingPeriodException extends RentalException {

    public InvalidRentingPeriodException() {
        super("Renting period is invalid.");
    }

    public InvalidRentingPeriodException(String message) {
        super(message);
    }

    public InvalidRentingPeriodException(String message, Throwable cause) {
        super(message, cause);
    }
}
