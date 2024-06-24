package peaksoft.restaurant.api;

import peaksoft.restaurant.dto.subCategoryDto.SubCategoryRequest;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.dto.subCategoryDto.SubCategoryPagination;
import peaksoft.restaurant.dto.subCategoryDto.SubCategoryResponse;
import peaksoft.restaurant.service.SubCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subCategory")
@Tag(name = "Subcategory-API")
public class SubCategoryApi {

    private final SubCategoryService subCategoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse saveSubCategory(@RequestParam @Valid Long categoryId, @RequestBody SubCategoryRequest subCategoryRequest){
        return subCategoryService.saveSucToCategory(categoryId, subCategoryRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllById")
    public List<SubCategoryResponse> getAllByCategoryId(@RequestParam Long categoryId){
        return subCategoryService.getAllSubCategoryByCategoryId(categoryId);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getById")
    public SubCategoryResponse getById(@RequestParam Long subCategoryId){
        return subCategoryService.getSubCategoryById(subCategoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public SimpleResponse deleteSubCategory(@RequestParam Long subCategoryId){
        return subCategoryService.deleteSubCategory(subCategoryId);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public SimpleResponse updateSubCategory(@RequestParam Long subCategoryId,@RequestBody SubCategoryRequest subCategoryRequest){
        return subCategoryService.updateSubCategory(subCategoryId,subCategoryRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public SubCategoryPagination getAllSubCategories(int currentPage, int pageSize){
        return subCategoryService.getAllSubCategories(currentPage,pageSize);
    }
}