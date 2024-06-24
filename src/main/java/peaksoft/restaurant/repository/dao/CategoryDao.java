package peaksoft.restaurant.repository.dao;

import peaksoft.restaurant.dto.categoryDto.CategoryPaginationResponse;
import peaksoft.restaurant.dto.categoryDto.CategoryResponse;

public interface CategoryDao {
    CategoryPaginationResponse getAllCategory(int currentPage, int pageSize);

    CategoryResponse getCategoryById(Long categoryId);
}
