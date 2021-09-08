package miniproject.KUrestaurant.domain;

public enum SortType {
    average_star("별점 높은 순"), eval_num("평가 많은 순");

    private final String description;

    SortType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}