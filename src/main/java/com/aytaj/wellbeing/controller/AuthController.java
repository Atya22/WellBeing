package com.aytaj.wellbeing.controller;


import com.aytaj.wellbeing.dto.*;
import com.aytaj.wellbeing.service.auth.AuthService;
import com.aytaj.wellbeing.service.auth.ClientRegistrationService;
import com.aytaj.wellbeing.service.auth.SpecialistRegistrationService;
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
//            @RequestPart(value = "additionalDiplomasFiles", required = false) List<MultipartFile> additionalDiplomas,
            @RequestPart(value = "certificates", required = false) List<MultipartFile> certificateFiles
    ) {
        specialistRegistrationService.registerSpecialist(request, diplomaFile, certificateFiles);
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
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok("Logout success");
    }
}


