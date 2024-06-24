package peaksoft.restaurant.dto.menuDto;

import lombok.Builder;
import java.math.BigDecimal;
@Builder
public record MenuResponse(
        Long id,
        String name,
        String image,
        BigDecimal price,
        String description,
        boolean isVegetarian) {
}