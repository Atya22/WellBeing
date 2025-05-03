package com.aytaj.wellbeing.controller;


import com.aytaj.wellbeing.dto.*;
import com.aytaj.wellbeing.service.AuthService;
import com.aytaj.wellbeing.service.SpecialistAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final SpecialistAuthService specialistAuthService;

    @PostMapping("/registration/otp-request")
    public void requestClientOtp(@Valid @RequestBody RegistrationOtpDto email) {
        authService.sendOtpRegistration(email);
    }

    @PostMapping(value = "/registration/client/otp-verification", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void verifyClientOtpAndRegister(@Valid @RequestBody ClientRegistrationRequest request) {
        authService.registerUser(request);
    }

    @PostMapping(value = "/registration/specialist/otp-verification")
    public ResponseEntity<String> verifySpecialistOtpAndRegisterApproving(
            @RequestPart("data") @Valid @ModelAttribute SpecialistRegistrationRequest request,
            @RequestPart("diploma") MultipartFile diplomaFile,
            @RequestPart(value = "certificates", required = false) List<MultipartFile> certificateFiles
    ) {
        specialistAuthService.registerSpecialist(request, diplomaFile, certificateFiles);
        return ResponseEntity.status(HttpStatus.CREATED).body("Specialist registration submitted for approval.");
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
}


