package peaksoft.restaurant.dto.authDto;

import peaksoft.restaurant.validation.EmailValidation;

public record SignInRequest(
        @EmailValidation
        String email,
        String password
) {
}