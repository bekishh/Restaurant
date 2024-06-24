package peaksoft.restaurant.repository.dao;

import peaksoft.restaurant.dto.menuDto.MenuPaginationResponse;
import peaksoft.restaurant.dto.menuDto.MenuResponse;
import peaksoft.restaurant.dto.menuDto.SearchResponse;
import peaksoft.restaurant.dto.menuDto.WholeMenu;

import java.util.List;

public interface MenuDao {
    MenuPaginationResponse getAll(int page, int pageSize);

    List<MenuResponse> filterByBoo(String word);

    List<MenuResponse> sortByPrice(String word);

    MenuResponse getById(Long menuId);

    List<SearchResponse> searchMenu(String word);

    List<WholeMenu> getAllMenuByChequeId(Long chequeId);
}
