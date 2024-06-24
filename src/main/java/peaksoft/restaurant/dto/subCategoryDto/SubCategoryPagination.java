package peaksoft.restaurant.dto.subCategoryDto;

import lombok.Builder;
import lombok.Getter;
import peaksoft.restaurant.dto.categoryDto.CategoryGroupByResponsePag;

import java.util.List;

@Builder
@Getter
public class SubCategoryPagination {
    private int currentPage;
    private int pageSize;
    private List<CategoryGroupByResponsePag> categoryGroupByResponsePagList;
}