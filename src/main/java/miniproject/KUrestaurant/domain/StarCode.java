package miniproject.KUrestaurant.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StarCode {

    private int star_num;
    private String star_display;

    public StarCode(int star_num, String star_display) {
        this.star_num = star_num;
        this.star_display = star_display;
    }
}

