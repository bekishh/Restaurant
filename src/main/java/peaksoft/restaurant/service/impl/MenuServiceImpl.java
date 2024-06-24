package peaksoft.restaurant.service.impl;

import peaksoft.restaurant.dto.menuDto.MenuRequest;
import peaksoft.restaurant.dto.menuDto.MenuPaginationResponse;
import peaksoft.restaurant.dto.menuDto.MenuResponse;
import peaksoft.restaurant.dto.menuDto.SearchResponse;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.entity.Menu;
import peaksoft.restaurant.entity.Restaurant;
import peaksoft.restaurant.entity.Subcategory;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.MenuRepository;
import peaksoft.restaurant.repository.RestaurantRepository;
import peaksoft.restaurant.repository.SubCategoryRepository;
import peaksoft.restaurant.repository.dao.impl.MenuJDBCTemplate;
import peaksoft.restaurant.service.MenuService;
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
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuJDBCTemplate menuJDBCTemplate;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public SimpleResponse saveMenu(Long restaurantId, MenuRequest menuRequest, Long subCategoryId) {
        Menu menu = new Menu();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> {
                    log.error(String.format("Menu with id:%s not found", menu.getId()));
                    return new NotFoundException(String.format("Manu with id:%s not found", menu.getId()));
                }
        );
        Subcategory subcategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
                () -> {
                    log.error(String.format("SubCategory with id:%s not found", subCategoryId));
                    return new NotFoundException(String.format("SubCategory with id:%s not found", subCategoryId));
                }
        );
        menu.setName(menuRequest.name());
        menu.setPrice(menuRequest.price());
        menu.setImage(menuRequest.image());
        menu.setDescription(menuRequest.description());
        menu.setVegetarian(menuRequest.isVegetarian());
        menu.setRestaurant(restaurant);
        menu.setSubcategory(subcategory);
        subcategory.getMenuList().add(menu);
        restaurant.getMenultemList().add(menu);
        menuRepository.save(menu);
        log.info(String.format("Menu with id:%s successfully saved", menu.getId()));
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Menu with id:%s successfully saved", menu.getId())
        );
    }

    @Override
    public MenuPaginationResponse getAll(int page, int pageSize) {
      return menuJDBCTemplate.getAll(page,pageSize);
    }

    @Override
    public MenuResponse getById(Long menuId) {
        return menuJDBCTemplate.getById(menuId);
    }

    @Override
    public SimpleResponse deleteMenu(Long menuId) {
        if (!menuRepository.existsById(menuId)) {
            log.error(String.format("Menu with id:%s not found", menuId));
            throw new NotFoundException(String.format("Menu with id:%s not found", menuId));
        }
        menuRepository.deleteById(menuId);
        log.info(String.format("Menu with id:%s successfully deleted", menuId));
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Menu with id:%s successfully deleted", menuId)
        );
    }

    @Override
    public SimpleResponse updateMenu(Long menuId, MenuRequest menuRequest) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> {
                    log.error(String.format("Menu with id:%s not found", menuId));
                    return new NotFoundException(String.format("Menu with id:%s not found", menuId));
                }
        );
        menu.setName(menuRequest.name());
        menu.setPrice(menuRequest.price());
        menu.setImage(menuRequest.image());
        menu.setDescription(menuRequest.description());
        menu.setVegetarian(menuRequest.isVegetarian());
        menuRepository.save(menu);
        log.info(String.format("New menu with id: %s successfully saved", menuId));
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("New menu with id: %s successfully saved", menuId));
    }

    @Override
    public List<SearchResponse> search(String word) {
        return menuJDBCTemplate.searchMenu(word);
    }

    @Override
    public List<MenuResponse> sortByPrice(String word) {
        if(word.equalsIgnoreCase("asc") || word.equalsIgnoreCase("desc")){
            return menuJDBCTemplate.sortByPrice(word);
        }
        throw new NotFoundException("I don’t understand how to display your request Please enter it correctly");
    }

    @Override
    public List<MenuResponse> filter(String word) {
        if (word.equalsIgnoreCase("true") || word.equalsIgnoreCase("false")) {
            return menuJDBCTemplate.filterByBoo(word);
        }
        throw new NotFoundException("Food and category with this name is NOT FOUND ");
    }
}