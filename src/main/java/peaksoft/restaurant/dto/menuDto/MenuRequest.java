package peaksoft.restaurant.dto.menuDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record MenuRequest(
        @NotNull
        String name,
        String image,
        @Column(nullable = false)
        BigDecimal price,
        String description,
        boolean isVegetarian
) {
}