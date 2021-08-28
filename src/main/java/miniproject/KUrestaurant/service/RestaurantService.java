package miniproject.KUrestaurant.service;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Long saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    @Transactional
    public void removeRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);
        restaurantRepository.remove(restaurant);
    }

    public List<Restaurant> findRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant findOne(Long restaurantId) {
        return restaurantRepository.findOne(restaurantId);
    }

}
