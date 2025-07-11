package com.aytaj.wellbeing.controller;


import com.aytaj.wellbeing.dto.auth.ClientRegistrationRequest;
import com.aytaj.wellbeing.dto.auth.RegistrationOtpDto;
import com.aytaj.wellbeing.dto.auth.SpecialistRegistrationRequest;
import com.aytaj.wellbeing.dto.auth.UserLoginDto;
import com.aytaj.wellbeing.dto.response.TokenResponse;
import com.aytaj.wellbeing.service.auth.AuthService;
import com.aytaj.wellbeing.service.auth.ClientRegistrationService;
import com.aytaj.wellbeing.service.auth.SpecialistRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final SpecialistRegistrationService specialistRegistrationService;
    private final ClientRegistrationService clientRegistrationService;

    @PostMapping("/registration/otp-request")
    public void requestUserOtp(@Valid @RequestBody RegistrationOtpDto email) {
        authService.sendOtpRegistration(email);
    }

    @PostMapping(value = "/registration/client/otp-verification")
    public void verifyClientOtpAndRegister(@Valid @RequestBody ClientRegistrationRequest request) {
        clientRegistrationService.registerClient(request);
    }

    @PostMapping(value = "/registration/specialist/otp-verification", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> verifySpecialistOtpAndRegisterApproving(
            @RequestPart("data") @Valid SpecialistRegistrationRequest request,
            @RequestPart("diploma") MultipartFile diplomaFile,
            @RequestPart(value = "certificates", required = false) List<MultipartFile> certificateFiles
    ) {
        specialistRegistrationService.registerSpecialist(request, diplomaFile, certificateFiles);
        return ResponseEntity.status(HttpStatus.CREATED).body("Specialist auth submitted for approval.");
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/login")
    public TokenResponse loginUser(@Valid @RequestBody UserLoginDto request, HttpServletResponse response) {
        return authService.login(request, response);
    }
}
