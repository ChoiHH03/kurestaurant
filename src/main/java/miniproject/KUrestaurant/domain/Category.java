package miniproject.KUrestaurant.domain;

public enum Category {
    KOREAN("한식"), CHINESE("중식"), ITALIAN("양식"), JAPANESE("일식"), CAFE("카페"), ETC("기타");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
