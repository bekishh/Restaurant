package peaksoft.restaurant.dto.authDto;

import lombok.Builder;
import peaksoft.restaurant.enums.Role;
import peaksoft.restaurant.validation.PasswordValidation;
import peaksoft.restaurant.validation.PhoneNumberValidation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SignUpRequest(
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        LocalDate dateOfBirth,
        @Email(message = "Email is invalid")
        String email,
        @PasswordValidation
        String password,
        @PhoneNumberValidation
        String phoneNumber,
        @Enumerated(EnumType.STRING)
        Role role,
        LocalDate experience
) {
}