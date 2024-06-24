package peaksoft.restaurant.dto.userDto;

import peaksoft.restaurant.enums.Role;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        LocalDate experience,
        Role role
) {
}