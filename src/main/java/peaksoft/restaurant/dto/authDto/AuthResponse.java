package peaksoft.restaurant.dto.authDto;

import peaksoft.restaurant.enums.Role;
import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        String email,
        Role role
) {
}