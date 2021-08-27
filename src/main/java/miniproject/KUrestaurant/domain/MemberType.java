package miniproject.KUrestaurant.domain;

public enum MemberType {
    OWNER("사장님"), CUSTOMER("손님");

    private final String description;

    MemberType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
