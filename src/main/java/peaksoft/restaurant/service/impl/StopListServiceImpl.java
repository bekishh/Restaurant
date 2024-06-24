package peaksoft.restaurant.service.impl;

import peaksoft.restaurant.dto.stopListDto.StopListRequest;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.dto.stopListDto.StopListPagination;
import peaksoft.restaurant.dto.stopListDto.StopListResponse;
import peaksoft.restaurant.entity.Menu;
import peaksoft.restaurant.entity.StopList;
import peaksoft.restaurant.exception.BadRequestException;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.MenuRepository;
import peaksoft.restaurant.repository.RestaurantRepository;
import peaksoft.restaurant.repository.StopListRepository;
import peaksoft.restaurant.repository.dao.impl.StopListJDBCTemplate;
import peaksoft.restaurant.service.StopListService;
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
public class StopListServiceImpl implements StopListService {

    private final StopListRepository stopListRepository;
    private final StopListJDBCTemplate stopListJDBCTemplate;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public SimpleResponse saveStopList(Long menuId, StopListRequest stopListRequest) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NotFoundException("Not found")
        );
        if (stopListRepository.existsStopListByDateAndMenuId(stopListRequest.date(), menuId)) {
            throw new BadRequestException("A stop list already exists for this menu and date.");
        }
        StopList stopList = StopList
                .builder()
                .date(stopListRequest.date())
                .menu(menu)
                .reason(stopListRequest.reason())
                .build();
        stopListRepository.save(stopList);
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("StopList with id: %s successfully saved", stopList.getId())
        );


    }

    @Override
    public StopListResponse getStopListById(Long stopListId) {
        if (!stopListRepository.existsById(stopListId)) {
            log.error(String.format("Stop List with id: %s is exists", stopListId));
            throw new NotFoundException(String.format("Stop List with id: %s is exists", stopListId));
        }
        return stopListJDBCTemplate.getStopListById(stopListId);
    }

    @Override
    public List<StopListResponse> getAllStopLists(Long restId) {
        if (!restaurantRepository.existsById(restId)) {
            log.error(String.format("Restaurant with id: %s not found", restId));
            throw new NotFoundException(String.format("Restaurant with id: %s not found", restId));
        }
        log.info("Successfully gedet all stopLists with restaurant id");
        return stopListJDBCTemplate.getAllStopLists(restId);
    }

    @Override
    public SimpleResponse deleteStopList(Long stopListId) {
        if (!stopListRepository.existsById(stopListId)) {
            log.error(String.format("StopList with id: %s not found", stopListId));
            throw new NotFoundException(String.format("StopList with id: %s not found", stopListId));
        }
        stopListRepository.deleteById(stopListId);
        log.info(String.format("StopList with id: %s successfully deleted", stopListId));
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("StopList with id: %s successfully deleted", stopListId)
        );
    }

    @Override
    public SimpleResponse updateStopList(Long stopListId, StopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(stopListId).orElseThrow(
                () -> {
                    log.error(String.format("StopList with id: %s not found", stopListId));
                    return new NotFoundException(String.format("StopList with id: %s not found", stopListId));
                }
        );
        stopList.setDate(stopListRequest.date());
        stopList.setReason(stopListRequest.reason());
        stopListRepository.save(stopList);
        log.info("Successfully updated");
        return new SimpleResponse(
                HttpStatus.OK,
                "Successfully update"
        );
    }

    @Override
    public StopListPagination getAll(int page, int pageSize) {
        return stopListJDBCTemplate.getAll(page, pageSize);
    }
}