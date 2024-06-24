package peaksoft.restaurant.dto.restaurantDto;

import peaksoft.restaurant.enums.RestType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RestaurantRequest(
        @NotNull
        @Column(unique = true)
        String name,
        @NotNull
        String location,

        @Enumerated(EnumType.STRING)
        RestType restType,
        BigDecimal service
) {
}