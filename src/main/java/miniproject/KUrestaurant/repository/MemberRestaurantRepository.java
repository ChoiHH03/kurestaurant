package miniproject.KUrestaurant.repository;

import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.MemberRestaurant;
import miniproject.KUrestaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRestaurantRepository extends JpaRepository<MemberRestaurant, Long>, MemberRestaurantRepositoryCustom {
    Optional<MemberRestaurant> findByMemberAndRestaurant(Member member, Restaurant restaurant);
}
