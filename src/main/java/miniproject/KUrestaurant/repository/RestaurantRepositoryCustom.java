package miniproject.KUrestaurant.repository;

import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.domain.RestaurantSearchCond;

import java.util.List;

public interface RestaurantRepositoryCustom {
    List<Restaurant> search(RestaurantSearchCond cond);
}
