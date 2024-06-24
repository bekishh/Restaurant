package peaksoft.restaurant.api;

import peaksoft.restaurant.dto.categoryDto.CategoryRequest;
import peaksoft.restaurant.dto.categoryDto.CategoryPaginationResponse;
import peaksoft.restaurant.dto.categoryDto.CategoryResponse;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/category")
@RequiredArgsConstructor
@RestController
@Tag(name = "Category-API")
public class CategoryApi {

    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse saveCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.saveCategory(categoryRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public CategoryPaginationResponse getAll(@RequestParam int page, @RequestParam int pageSize){
        return categoryService.getAllCategory(page,pageSize);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getById")
    public CategoryResponse getById(@RequestParam Long categoryId){
        return categoryService.getByIdCategory(categoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public SimpleResponse deleteCategory(@RequestParam Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public SimpleResponse updateCategory(@RequestParam Long categoryId,@RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategory(categoryId,categoryRequest);
    }
}