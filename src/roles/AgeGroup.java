package roles;

public enum AgeGroup {

    JUNIOR(10),
    EXPERIENCED(0),
    SENIOR(15);

    private int tax;
    AgeGroup(int tax){
        this.tax = tax;
    }

    public int getTax() {
        return tax;
    }

}