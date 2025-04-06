package vehicles;

public enum FuelType {

    DIESEL(3),
    PETROL(3),
    HYBRID(1),
    ELECTRICITY(0),
    HYDROGEN(0);

    private double tax;
    FuelType(double tax) {
        this.tax = tax;
    }

    public double getTax(){
        return tax;
    }

}