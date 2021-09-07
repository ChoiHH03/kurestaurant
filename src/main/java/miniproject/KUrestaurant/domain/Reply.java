package miniproject.KUrestaurant.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {

    @Id
    @GeneratedValue
    @Column(name="reply_id")
    private Long id;

    @NotNull
    @Range(min=1, max=5)
    private int star;

    private String comment;

    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    private Restaurant restaurant;

    public Reply(Member member, Restaurant restaurant, int star, String comment, LocalDate date) {
        this.member = member;
        this.restaurant = restaurant;
        this.star = star;
        this.comment = comment;
        this.date = date;

        member.getReplies().add(this);
        restaurant.getReplies().add(this);
        restaurant.addStar(this);
    }

    //==제거 메서드==//
    public static void removeReply(Reply reply) {
        Member member = reply.getMember();
        Restaurant restaurant = reply.getRestaurant();

        member.getReplies().remove(reply);
        restaurant.getReplies().remove(reply);
        restaurant.removeStar(reply);
    }
}
