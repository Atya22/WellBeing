package com.aytaj.wellbeing.controller;


import com.aytaj.wellbeing.dto.ClientRegisterRequest;
import com.aytaj.wellbeing.dto.RegistrationOtpDto;
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
//    private final ClientService clientService;

//    @PostMapping("/login")

//    @PostMapping("/registration/client/")
//    public ResponseEntity<?> registerClient(@RequestBody ClientRegisterRequest request) {
//        return ResponseEntity.ok(clientService.register(request));
//    }

    @PostMapping("/registration/client/sendOtp")
    public void registerClient(@Valid @RequestBody RegistrationOtpDto email) {
        authService.sendOtpRegistration(email);
    }

    @PostMapping("/registration/client/verify")
    public void addClient(@Valid @RequestBody ClientRegisterRequest request) {
        authService.registerUser(request);
    }

//    @PostMapping("/registration/specialist/")
//
//    login, registration, logout, password change, access token, refresh token
}


