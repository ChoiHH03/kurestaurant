package miniproject.KUrestaurant.repository;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.MemberRestaurant;
import miniproject.KUrestaurant.domain.Restaurant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MemberRestaurantRepository {

    private final EntityManager em;

    public void save(MemberRestaurant memberRestaurant) {
        em.persist(memberRestaurant);
    }

    public void remove(MemberRestaurant memberRestaurant) {
        em.remove(memberRestaurant);
    }

    public List<Restaurant> findByMember(Member member){
        return member.getMemberRestaurants()
                .stream()
                .map(m -> m.getRestaurant())
                .collect(Collectors.toList());
    }

    public Optional<MemberRestaurant> findOne(Member member, Restaurant restaurant) {
        return member.getMemberRestaurants()
                .stream().filter(m -> m.getRestaurant() == restaurant)
                .findFirst();
    }

    public MemberRestaurant findById(Long memberRestaurantId) {
        return em.find(MemberRestaurant.class, memberRestaurantId);
    }
}
