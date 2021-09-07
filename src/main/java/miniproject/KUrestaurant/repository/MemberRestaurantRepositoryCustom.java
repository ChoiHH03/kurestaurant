package miniproject.KUrestaurant.repository;

import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.Restaurant;

import java.util.List;

public interface MemberRestaurantRepositoryCustom {
    List<Restaurant> findByMember(Member member);
}
