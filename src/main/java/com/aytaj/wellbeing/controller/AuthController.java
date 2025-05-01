package com.aytaj.wellbeing.controller;


import com.aytaj.wellbeing.dto.ClientRegisterRequest;
import com.aytaj.wellbeing.dto.TokenResponse;
import com.aytaj.wellbeing.dto.RegistrationOtpDto;
import com.aytaj.wellbeing.dto.UserLoginDto;
import com.aytaj.wellbeing.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration/client/otp-request")
    public void requestClientOtp(@Valid @RequestBody RegistrationOtpDto email) {
        authService.sendOtpRegistration(email);
    }

    @PostMapping("/registration/client/otp-verification")
    public void verifyClientOtpAndRegister(@Valid @RequestBody ClientRegisterRequest request) {
        authService.registerUser(request);
    }

    @PostMapping("/login")
    public TokenResponse loginUser(@Valid @RequestBody UserLoginDto request) {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        authService.logout(request);
    }

//    @PostMapping("/registration/specialist/")
//
//    password change
}


