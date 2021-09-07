package miniproject.KUrestaurant.service;

import miniproject.KUrestaurant.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class RestaurantServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @Test
    public void 레스토랑등록() {
        Member member = new Member("member", "loginId", "member", MemberType.OWNER);
        Restaurant restaurant = new Restaurant("restaurant", "02-000-0000", "서울", Category.CAFE, true, member, null);

        Long id = restaurantService.saveRestaurant(restaurant);

        assertEquals(restaurantService.findOne(id), restaurant);
    }

    @Test
    public void 레스토랑삭제() {
        Member member = new Member("member", "loginId", "member", MemberType.OWNER);
        Restaurant restaurant = new Restaurant("restaurant", "02-000-0000", "서울", Category.CAFE, true, member, null);
        Long id = restaurantService.saveRestaurant(restaurant);
        restaurantService.removeRestaurant(id);

        assertEquals(restaurantService.findRestaurants().size(),0);
    }

}
