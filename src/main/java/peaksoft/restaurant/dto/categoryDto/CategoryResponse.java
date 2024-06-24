package peaksoft.restaurant.dto.categoryDto;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Long id,
        String name
) {

    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}