package miniproject.KUrestaurant.domain;

import lombok.Data;

@Data
public class RestaurantSearchCond {
    private String name;
    private Long star;
    private Long eval_num;
    private Category category;
}
