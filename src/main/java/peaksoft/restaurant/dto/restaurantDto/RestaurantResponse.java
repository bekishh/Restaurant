package peaksoft.restaurant.dto.restaurantDto;

import peaksoft.restaurant.enums.RestType;
import lombok.Builder;

@Builder
public record RestaurantResponse(
        Long id,
        String name,
        String location,
        RestType restType,
        int numberOfEmployees,
        int service
) {
}