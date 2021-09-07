package miniproject.KUrestaurant.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_restaurant")
public class MemberRestaurant {

    @Id
    @GeneratedValue
    @Column(name = "member_restaurant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    //==생성 메서드==//
    public MemberRestaurant(Member member, Restaurant restaurant) {
        this.member = member;
        member.getMemberRestaurants().add(this);
        this.restaurant = restaurant;
        restaurant.getMemberRestaurants().add(this);
    }

    //==제거 메서드==//
    public static void removeMemberRestaurant(MemberRestaurant memberRestaurant) {
        Member member = memberRestaurant.getMember();
        Restaurant restaurant = memberRestaurant.getRestaurant();

        member.getMemberRestaurants().remove(memberRestaurant);
        restaurant.getMemberRestaurants().remove(memberRestaurant);
    }
}
