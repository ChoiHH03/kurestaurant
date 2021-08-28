package miniproject.KUrestaurant.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Restaurant {

    @Id
    @GeneratedValue
    @Column(name = "restaurant_id")
    private Long id;

    @NotEmpty(message = "식당명을 입력해주세요.")
    private String name;

    @NotEmpty(message = "전화번호를 입력해주세요")
    private String phoneNumber;
    private Long star = 0L;
    private Long eval_num = 0L;
    private float average_star;

    @Lob
    private Blob thumbNail;

    @NotEmpty(message = "주소를 입력해주세요.")
    private String address;

    @NotNull(message = "카테고리를 선택해주세요.")
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    private boolean delivery;

    @OneToMany(mappedBy = "restaurant")
    private List<MemberRestaurant> memberRestaurants = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Reply> replies = new ArrayList<>();


    //==비즈니스 로직==//
    /** 평균 별점 계산 */
    public void calcAverageStar() {
        this.average_star = (this.eval_num > 0) ? (float)this.star / this.eval_num : 0;
    }

    /** 별점 추가 */
    public void addStar(Reply reply) {
        star = star + reply.getStar();
        eval_num++;
        this.calcAverageStar();
    }

    /** 별점 제거 */
    public void removeStar(Reply reply) {
        star = star - reply.getStar();
        eval_num--;
        this.calcAverageStar();
    }
}
