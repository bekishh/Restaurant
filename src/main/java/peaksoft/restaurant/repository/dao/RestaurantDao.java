package peaksoft.restaurant.repository.dao;

import peaksoft.restaurant.dto.restaurantDto.RestaurantResponse;

import java.util.List;

public interface RestaurantDao {
    List<RestaurantResponse> getAllRestaurant();

    RestaurantResponse getById(Long restaurantId);
}
