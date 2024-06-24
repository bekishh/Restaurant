package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.menuDto.MenuRequest;
import peaksoft.restaurant.dto.menuDto.MenuPaginationResponse;
import peaksoft.restaurant.dto.menuDto.MenuResponse;
import peaksoft.restaurant.dto.menuDto.SearchResponse;
import peaksoft.restaurant.dto.SimpleResponse;
import java.util.List;

public interface MenuService {

    SimpleResponse saveMenu(Long restaurantId, MenuRequest menuRequest,Long subCategoryId);
    MenuPaginationResponse getAll(int page, int pageSize);
    MenuResponse getById(Long menuId);
    SimpleResponse deleteMenu(Long menuId);
    SimpleResponse updateMenu(Long menuId , MenuRequest menuRequest);
    List<SearchResponse> search (String word);
    List<MenuResponse>sortByPrice(String word);
    List<MenuResponse>filter(String word);
}