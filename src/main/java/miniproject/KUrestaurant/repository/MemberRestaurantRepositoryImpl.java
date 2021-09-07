package miniproject.KUrestaurant.repository;

import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class MemberRestaurantRepositoryImpl implements MemberRestaurantRepositoryCustom {
    @Override
    public List<Restaurant> findByMember(Member member) {
        return member.getMemberRestaurants()
                .stream()
                .map(m -> m.getRestaurant())
                .collect(Collectors.toList());
    }
}
