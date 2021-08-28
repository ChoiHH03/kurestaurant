package miniproject.KUrestaurant.web.form;

import lombok.Data;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.Restaurant;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class ReplyForm {

    @NotNull
    @Range(min=1, max=5, message = "별점을 입력하세요")
    private int star;
    private String comment;
    private Member member;
    private Restaurant restaurant;
}
