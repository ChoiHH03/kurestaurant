package miniproject.KUrestaurant.service;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.domain.RestaurantSearchCond;
import miniproject.KUrestaurant.repository.RestaurantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        restaurantRepository.delete(restaurant);
    }

    @Transactional
    public void editRestaurant(Long restaurantId, String name, String phoneNumber, String address, boolean delivery, String image) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        restaurant.edit(name, phoneNumber, address, delivery, image);
    }

    public List<Restaurant> findRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant findOne(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).get();
    }

    public Page<Restaurant> findRestaurantsCond(RestaurantSearchCond cond, Pageable pageable) {
        return restaurantRepository.searchAndSort(cond, pageable);
    }
}
