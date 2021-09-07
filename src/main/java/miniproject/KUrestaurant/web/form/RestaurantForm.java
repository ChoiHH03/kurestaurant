package miniproject.KUrestaurant.web.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import miniproject.KUrestaurant.domain.Category;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.MemberRestaurant;
import miniproject.KUrestaurant.domain.Reply;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class RestaurantForm {

    @NotEmpty(message = "식당명을 입력해주세요.")
    private String name;

    @NotEmpty(message = "전화번호를 입력해주세요")
    private String phoneNumber;

    private MultipartFile attachFile;

    @NotEmpty(message = "주소를 입력해주세요.")
    private String address;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;

    @NotNull
    private boolean delivery;
}
