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
    public TokenResponse loginUser(@Valid @RequestBody UserLoginDto request){
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(HttpServletRequest request){
        String token = (String) request.getAttribute("token");
        return authService.refreshToken(token);
    }

    @PostMapping("/logout")
    public void   logout(HttpServletRequest request){
        String token = (String) request.getAttribute("token");
        authService.logout(token);
    }

//    @PostMapping("/registration/specialist/")
//
//    password change
}


