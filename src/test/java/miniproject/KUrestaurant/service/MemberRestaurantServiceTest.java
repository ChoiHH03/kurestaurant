package miniproject.KUrestaurant.service;

import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.MemberRestaurant;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRestaurantServiceTest {

    @Autowired
    MemberRestaurantService memberRestaurantService;

    @Test
    public void 레스토랑찜() {

        Member member = new Member();
        Restaurant restaurant = new Restaurant();

        Long id = memberRestaurantService.pickRestaurant(member, restaurant);

        assertEquals(memberRestaurantService.findOne(member, restaurant),memberRestaurantService.findById(id));
        assertEquals(member.getMemberRestaurants().size(),1);
    }

    @Test
    public void 레스토랑찜해제() {

        Member member = new Member();
        Restaurant restaurant = new Restaurant();

        Long id = memberRestaurantService.pickRestaurant(member, restaurant);
        memberRestaurantService.unpickRestaurant(member, restaurant);

        assertEquals(member.getMemberRestaurants().size(), 0);
    }
}