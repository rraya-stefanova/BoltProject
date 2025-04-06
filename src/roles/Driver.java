package roles;

public class Driver {

    public AgeGroup ageGroup;

    public Driver(AgeGroup group){
        this.ageGroup = group;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }
    public int calculateTax() {
        return this.ageGroup.getTax();
    }

}
