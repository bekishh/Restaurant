package peaksoft.restaurant.dto.categoryDto;

import lombok.Builder;
import java.util.List;

@Builder
public record CategoryGroupByResponsePag(
        Long categoryId,
        String name,
        List<String> names
) {
}