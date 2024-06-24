package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.subCategoryDto.SubCategoryRequest;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.dto.subCategoryDto.SubCategoryPagination;
import peaksoft.restaurant.dto.subCategoryDto.SubCategoryResponse;
import java.util.List;

public interface SubCategoryService {
    SimpleResponse saveSucToCategory(Long categoryId, SubCategoryRequest subCategoryRequest);
    List<SubCategoryResponse> getAllSubCategoryByCategoryId(Long categoryId);
    SubCategoryResponse getSubCategoryById(Long subCategoryId);
    SimpleResponse deleteSubCategory(Long subCategoryId);
    SimpleResponse updateSubCategory(Long subCategoryId,SubCategoryRequest subCategoryRequest);
    SubCategoryPagination getAllSubCategories(int currentPage, int pageSize);
}