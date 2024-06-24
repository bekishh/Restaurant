package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.authDto.AuthResponse;
import peaksoft.restaurant.dto.authDto.SignInRequest;
import peaksoft.restaurant.dto.authDto.SignUpRequest;

public interface AuthService {
    AuthResponse signUp(SignUpRequest signUpRequest);
    AuthResponse signIn(SignInRequest signInRequest);
}
