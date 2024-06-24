package peaksoft.restaurant.dto.subCategoryDto;

import jakarta.validation.constraints.NotNull;

public record SubCategoryRequest(
        @NotNull
        String name
) {
}