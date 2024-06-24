package peaksoft.restaurant.api;

import peaksoft.restaurant.dto.authDto.AuthResponse;
import peaksoft.restaurant.dto.authDto.SignInRequest;
import peaksoft.restaurant.dto.authDto.SignUpRequest;
import peaksoft.restaurant.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth-API")
public class AuthApi {

    private final AuthService authService;

//    @PostMapping("/signUp")
//    public AuthResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
//        return authService.signUp(signUpRequest);
//    }

    @PostMapping("/signIn")
    public AuthResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }
}