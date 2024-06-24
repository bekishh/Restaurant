package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.restaurantDto.RestaurantRequest;
import peaksoft.restaurant.dto.restaurantDto.RestaurantResponse;
import peaksoft.restaurant.dto.SimpleResponse;
import java.util.List;

public interface RestaurantService {
    SimpleResponse saveRestaurant (RestaurantRequest restaurantRequest);
    List<RestaurantResponse> getAll();
    RestaurantResponse getRestaurantById(Long restaurantId);
    SimpleResponse deleteRestaurant(Long restaurantId);
    SimpleResponse updateRestaurant(Long restaurantId,RestaurantRequest restaurantRequest);
}