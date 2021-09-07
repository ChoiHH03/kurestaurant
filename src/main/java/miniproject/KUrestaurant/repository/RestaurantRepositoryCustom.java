package miniproject.KUrestaurant.repository;

import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.domain.RestaurantSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestaurantRepositoryCustom {
    List<Restaurant> search(RestaurantSearchCond cond);

    Page<Restaurant> searchPage(RestaurantSearchCond cond, Pageable pageable);
    Page<Restaurant> sortByEvalNum(RestaurantSearchCond cond, Pageable pageable);
}
