package peaksoft.restaurant.repository.dao;

import peaksoft.restaurant.dto.subCategoryDto.SubCategoryPagination;
import peaksoft.restaurant.dto.subCategoryDto.SubCategoryResponse;

import java.util.List;

public interface SubCategoryDao {
    List<SubCategoryResponse> getAllSubCategoryWithCategoriesId(Long categoryId);

    SubCategoryResponse getSubCategoryById(Long subCategoryId);

    SubCategoryPagination getAllSubCategories(int currentPage, int pageSize);
}
