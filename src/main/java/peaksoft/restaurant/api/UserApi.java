package peaksoft.restaurant.api;

import peaksoft.restaurant.dto.authDto.SignUpRequest;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.dto.userDto.UserResponse;
import peaksoft.restaurant.dto.userDto.UserResponsePagination;
import peaksoft.restaurant.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Tag(name = "User-API")
public class UserApi {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public UserResponsePagination getAllUser(@RequestParam int currentPage, @RequestParam int pageSize) {
        return userService.getAll( currentPage,pageSize);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getById")
    public UserResponse getById(@RequestParam @Valid Long userId) {
        return userService.getById(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public SimpleResponse deleteUser(@RequestParam @Valid Long userId) {
        return userService.deleteUser(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public SimpleResponse updateUser(@RequestBody @Valid SignUpRequest signUpRequest, @RequestParam Long userId) {
        return userService.updateUser(userId, signUpRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/accept")
    public SimpleResponse acceptOrRejectUser(@RequestParam Long userId, @RequestParam String word ,@RequestParam Long restaurantId) {
        return userService.acceptOrRejectUser(userId, word, restaurantId );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveUser")
    public SimpleResponse saveUser(@RequestParam Long restaurantId, @RequestBody SignUpRequest signUpRequest) {
        return userService.saveUser(restaurantId, signUpRequest);
    }
}