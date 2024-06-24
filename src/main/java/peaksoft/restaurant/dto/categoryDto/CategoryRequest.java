package peaksoft.restaurant.dto.categoryDto;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull
        String name
) {
}