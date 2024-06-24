package peaksoft.restaurant.service.impl;

import peaksoft.restaurant.dto.restaurantDto.RestaurantRequest;
import peaksoft.restaurant.dto.restaurantDto.RestaurantResponse;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.entity.Restaurant;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.RestaurantRepository;
import peaksoft.restaurant.repository.dao.impl.RestaurantDaoImpl;
import peaksoft.restaurant.service.RestaurantService;
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
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantDaoImpl restaurantDaoImpl;

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setService(restaurantRequest.service());
        restaurant.setRestType(restaurantRequest.restType());
        restaurantRepository.save(restaurant);
        return new SimpleResponse(
                HttpStatus.OK,
                ("New restaurant successfully saved")
        );
    }

    @Override
    public List<RestaurantResponse> getAll() {
        return restaurantDaoImpl.getAllRestaurant();
    }

    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {
        return restaurantDaoImpl.getById(restaurantId);
    }

    @Override
    public SimpleResponse deleteRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            log.error(String.format("Restaurant with id:%s is exists", restaurantId));
            throw new NotFoundException(String.format("Restaurant with id:%s is exists", restaurantId));
        }
        restaurantRepository.deleteById(restaurantId);
        log.info(String.format("Restaurant with id:%s successfully deleted",restaurantId));
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Restaurant with id:%s successfully deleted",restaurantId)
        );
    }

    @Override
    public SimpleResponse updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest) {
       Restaurant restaurant= restaurantRepository.findById(restaurantId).orElseThrow(
                ()->new NotFoundException(
                        String.format("Restaurant with id:%s not found ",restaurantId)
                )
        );
       restaurant.setName(restaurantRequest.name());
       restaurant.setLocation(restaurantRequest.location());
       restaurant.setService(restaurantRequest.service());
       restaurant.setRestType(restaurantRequest.restType());
       restaurantRepository.save(restaurant);
       log.info(String.format("Restaurant with id:%s successfully updated",restaurantId));
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Restaurant with id:%s successfully updated",restaurantId)
        );
    }
}
