package miniproject.KUrestaurant;

import miniproject.KUrestaurant.domain.*;
import miniproject.KUrestaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class JPARepositoryTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void search() {
        Member member = new Member("member", "loginId", "password", MemberType.OWNER);
        Restaurant restaurant1 = new Restaurant("restaurant1", "02-000-000",
                "서울", Category.CAFE, true, member, null);
        Restaurant restaurant2 = new Restaurant("restaurant2", "02-000-000",
                "서울", Category.CAFE, true, member, null);

        em.persist(member);
        em.persist(restaurant1);
        em.persist(restaurant2);

        RestaurantSearchCond cond = new RestaurantSearchCond();
        cond.setName("restaurant");
        cond.setCategory(Category.CAFE);

        List<Restaurant> result = restaurantRepository.search(cond);
        Restaurant restaurant = result.get(0);
        assertEquals(result.size(),2);
        assertEquals(restaurant, restaurant1);
    }
}
