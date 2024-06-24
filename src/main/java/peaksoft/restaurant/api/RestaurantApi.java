package peaksoft.restaurant.api;

import peaksoft.restaurant.dto.restaurantDto.RestaurantRequest;
import peaksoft.restaurant.dto.restaurantDto.RestaurantResponse;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
@Tag(name = "Restaurant-API")
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse saveRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<RestaurantResponse> getAll() {
        return restaurantService.getAll();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getById")
    public RestaurantResponse getById(Long restaurantId) {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public SimpleResponse deleteRestaurant(Long restaurantId) {
        return restaurantService.deleteRestaurant(restaurantId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public SimpleResponse update(@RequestParam Long restaurantId,@RequestBody  RestaurantRequest request) {
        return restaurantService.updateRestaurant(restaurantId,request);
    }
}