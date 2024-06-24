package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.categoryDto.CategoryRequest;
import peaksoft.restaurant.dto.categoryDto.CategoryPaginationResponse;
import peaksoft.restaurant.dto.categoryDto.CategoryResponse;
import peaksoft.restaurant.dto.SimpleResponse;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    CategoryPaginationResponse getAllCategory(int page,int pageSize);
    CategoryResponse getByIdCategory (Long categoryId);
    SimpleResponse deleteCategory(Long categoryId);
    SimpleResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);
}
