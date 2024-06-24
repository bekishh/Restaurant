package peaksoft.restaurant.dto.menuDto;

import java.math.BigDecimal;

public record WholeMenu(
        Long id,
        String categoryName,
        String subCategoryName,
        String name,
        BigDecimal price
) {
}