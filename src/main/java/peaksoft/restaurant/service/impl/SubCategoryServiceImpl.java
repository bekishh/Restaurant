package peaksoft.restaurant.service.impl;

import peaksoft.restaurant.dto.subCategoryDto.SubCategoryRequest;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.dto.subCategoryDto.SubCategoryPagination;
import peaksoft.restaurant.dto.subCategoryDto.SubCategoryResponse;
import peaksoft.restaurant.entity.Category;
import peaksoft.restaurant.entity.Subcategory;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.CategoryRepository;
import peaksoft.restaurant.repository.MenuRepository;
import peaksoft.restaurant.repository.SubCategoryRepository;
import peaksoft.restaurant.repository.dao.impl.SubCategoryDaoImpl;
import peaksoft.restaurant.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService {
    private final MenuRepository menuRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryDaoImpl subCategoryDaoImpl;
    private final CategoryRepository categoryRepository;

    @Override
    public SimpleResponse saveSucToCategory(Long categoryId, SubCategoryRequest subCategoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.error(String.format("SubCategory with id:%s  not found!", categoryId));
            return new NotFoundException(String.format("SubCategory with id:%s  not found!", categoryId));
        });
        Subcategory subcategory = new Subcategory();
        subcategory.setName(subCategoryRequest.name());
        subcategory.setCategory(category);
        category.getSubcategory().add(subcategory);
        subCategoryRepository.save(subcategory);
        categoryRepository.save(category);
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("SubCategory with id:%s successfully saved to category with id:%s ", subcategory.getId(), categoryId)
        );
    }

    @Override
    public List<SubCategoryResponse> getAllSubCategoryByCategoryId(Long categoryId) {
        log.info("Subcategory successfully gedet");
        return subCategoryDaoImpl.getAllSubCategoryWithCategoriesId(categoryId);
    }

    @Override
    public SubCategoryResponse getSubCategoryById(Long subCategoryId) {
        if (!subCategoryRepository.existsById(subCategoryId)) {
            log.error(String.format("SubCategory with id:%s not found or is exists", subCategoryId));
            throw new NotFoundException(String.format("SubCategory with id:%s not found or is exists", subCategoryId));
        }
        log.info(String.format("SubCategory with id:%s successfully foundet", subCategoryId));
        return subCategoryDaoImpl.getSubCategoryById(subCategoryId);
    }

    @Override
    public SimpleResponse deleteSubCategory(Long subCategoryId) {
        if (subCategoryRepository.existsById(subCategoryId)) {
            Subcategory subCategory = subCategoryRepository.findById(subCategoryId)
                    .orElseThrow(() ->
                            new NotFoundException("SubCategory with id: " + subCategoryId + " not found!"));
            subCategory.setCategory(new Category());
            menuRepository.deleteAll(subCategory.getMenuList());
            subCategoryRepository.deleteById(subCategoryId);

            log.info("SubCategory with id:%s successfully deleted");
            return new SimpleResponse(
                    HttpStatus.OK,
                    String.format("SUbCategory with id:%s successfully deleted and endet in subCategoryServiceImpl", subCategoryId)
            );
        }
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Successfully ??? id: %s",subCategoryId)
        );
    }

    @Override
    public SimpleResponse updateSubCategory(Long subCategoryId, SubCategoryRequest subCategoryRequest) {
        Subcategory subcategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
                (() -> {
                    log.error(String.format("Category with id:%s not found", subCategoryId));
                    throw new NotFoundException(
                            (String.format("Category with id:%s not found", subCategoryId)));
                }));
        subcategory.setName(subCategoryRequest.name());
        subCategoryRepository.save(subcategory);
        log.info(String.format("SubCategory with id:%s successfully updated", subCategoryId));

        return new SimpleResponse(
                HttpStatus.OK,
                String.format("SUbCategory with id:%s successfully deleted", subCategoryId)
        );
    }

    @Override
    public SubCategoryPagination getAllSubCategories(int currentPage, int pageSize) {
        log.info("SubCategory successfully gedet with pagination");
        return subCategoryDaoImpl.getAllSubCategories(currentPage, pageSize);
    }
}
