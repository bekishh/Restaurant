package peaksoft.restaurant.dto.menuDto;

import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record SearchResponse(
        Long id,
        String sucCategoryName,
        String categoryName,
        String foodName,
        String image,
        BigDecimal price,
        String description,
        boolean isVegetarian
) {
}