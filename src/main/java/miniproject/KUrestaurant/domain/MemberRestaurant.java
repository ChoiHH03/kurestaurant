package miniproject.KUrestaurant.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
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
    public static MemberRestaurant pickMemberRestaurant(Member member, Restaurant restaurant) {
        MemberRestaurant memberRestaurant = new MemberRestaurant();

        memberRestaurant.setMember(member);
        member.getMemberRestaurants().add(memberRestaurant);
        memberRestaurant.setRestaurant(restaurant);
        restaurant.getMemberRestaurants().add(memberRestaurant);

        return memberRestaurant;
    }

    //==제거 메서드==//
    public static void removeMemberRestaurant(MemberRestaurant memberRestaurant) {
        Member member = memberRestaurant.getMember();
        Restaurant restaurant = memberRestaurant.getRestaurant();

        member.getMemberRestaurants().remove(memberRestaurant);
        restaurant.getMemberRestaurants().remove(memberRestaurant);
    }
}
