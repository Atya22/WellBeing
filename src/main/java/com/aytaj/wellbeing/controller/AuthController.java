package com.aytaj.wellbeing.controller;


import com.aytaj.wellbeing.dto.ClientRegisterRequest;
import com.aytaj.wellbeing.dto.TokenResponse;
import com.aytaj.wellbeing.dto.RegistrationOtpDto;
import com.aytaj.wellbeing.dto.UserLoginDto;
import com.aytaj.wellbeing.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public TokenResponse loginUser(@Valid @RequestBody UserLoginDto request){
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public String refreshToken(@RequestBody String refreshToken){
        return authService.refreshToken(refreshToken);
    }

//    @PostMapping("/registration/specialist/")
//
//    login, registration, logout, password change, access token, refresh token
}


