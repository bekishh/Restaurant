package peaksoft.restaurant.dto.categoryDto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class CategoryPaginationResponse {
    private int currentPage;
    private int pageSize;
    private List<CategoryResponse> categoryResponseList;
}