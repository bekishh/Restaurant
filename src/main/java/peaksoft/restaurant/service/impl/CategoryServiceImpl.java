package peaksoft.restaurant.service.impl;

import peaksoft.restaurant.dto.categoryDto.CategoryRequest;
import peaksoft.restaurant.dto.categoryDto.CategoryPaginationResponse;
import peaksoft.restaurant.dto.categoryDto.CategoryResponse;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.entity.Category;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.CategoryRepository;
import peaksoft.restaurant.repository.dao.impl.CategoryDaoImpl;
import peaksoft.restaurant.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDaoImpl categoryDaoImpl;

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        log.info(String.format("Category with id:%s successfully saved", category.getId()));
        return new SimpleResponse(
                HttpStatus.OK,
                "Category successfully saved"
        );
    }

    @Override
    public CategoryPaginationResponse getAllCategory(int page, int pageSize) {
        log.info("All categories exit");
        return categoryDaoImpl.getAllCategory(page,pageSize);
    }

    @Override
    public CategoryResponse getByIdCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            log.error(String.format("Category with id %s not found", categoryId));
            throw new NotFoundException(
                    String.format("Category with id %s not found", categoryId));
        }
        log.info(String.format("Category with id %s successfully get", categoryId));
        return categoryDaoImpl.getCategoryById(categoryId);
    }

    @Override
    public SimpleResponse deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            log.error(String.format("Category with id %s not found", categoryId));
            throw new NotFoundException(
                    String.format("Category with id %s not found", categoryId));
        }
        categoryRepository.deleteById(categoryId);
        log.info(String.format("Category with id:%s successfully deleted", categoryId));
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Category with id: %s successfully deleted", categoryId)
        );
    }

    @Override
    public SimpleResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> {
                    log.error(String.format("Category with id:%s not found", categoryId));
                    return new NotFoundException(
                            (String.format("Category with id:%s not found", categoryId)));
                }
        );
        category.setName(categoryRequest.name());
        log.info(String.format("Category with id:%s successfully updated", categoryId));
        categoryRepository.save(category);
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Category with id:%s successfully updated", categoryId)
        );
    }
}