package peaksoft.restaurant.repository.dao;

import peaksoft.restaurant.dto.userDto.UserResponse;
import peaksoft.restaurant.dto.userDto.UserResponsePagination;

public interface UserDao {
    UserResponsePagination getAllUsers(int currentPage, int pageSize);

    UserResponse getUserById(Long userId);
}
