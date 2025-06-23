package com.aytaj.wellbeing.service.auth;

import com.aytaj.wellbeing.dto.auth.RegisterRequest;
import com.aytaj.wellbeing.dto.auth.RegistrationOtpDto;
import com.aytaj.wellbeing.dto.auth.UserLoginDto;
import com.aytaj.wellbeing.dto.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    void sendOtpRegistration(RegistrationOtpDto email);

    Boolean otpRegistrationVerification(RegisterRequest request);

    TokenResponse login(UserLoginDto dto, HttpServletResponse response);

    TokenResponse refreshToken(HttpServletRequest request);

    void logout(HttpServletRequest request, HttpServletResponse response);

}
