package miniproject.KUrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter @Setter
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

    //==생성 메서드==//
    public static Reply createReply(Member member, Restaurant restaurant, int star, String comment, LocalDate date) {
        Reply reply = new Reply();
        reply.setStar(star);
        reply.setComment(comment);
        reply.setDate(date);

        reply.setMember(member);
        member.getReplies().add(reply);
        reply.setRestaurant(restaurant);
        restaurant.getReplies().add(reply);
        restaurant.addStar(reply);

        return reply;
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
