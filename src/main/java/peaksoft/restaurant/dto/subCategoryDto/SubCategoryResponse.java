package peaksoft.restaurant.dto.subCategoryDto;

import lombok.Builder;

@Builder
public record SubCategoryResponse(
        Long id,
        String name,
        Long categoryId
) {
}