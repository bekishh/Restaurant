package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.authDto.SignUpRequest;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.dto.userDto.UserResponse;
import peaksoft.restaurant.dto.userDto.UserResponsePagination;

public interface UserService {
    SimpleResponse saveUser(Long restaurantId, SignUpRequest signUpRequest);
    SimpleResponse acceptOrRejectUser(Long userId, String word, Long restaurantId);
    UserResponsePagination getAll(int currentPage,int pageSize);
    UserResponse getById(Long userId);
    SimpleResponse deleteUser(Long userId);
    SimpleResponse updateUser(Long userId, SignUpRequest signUpRequest);
}
