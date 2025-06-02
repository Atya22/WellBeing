package com.aytaj.wellbeing.service.auth;

import com.aytaj.wellbeing.dto.auth.RegisterRequest;
import com.aytaj.wellbeing.dto.auth.RegistrationOtpDto;
import com.aytaj.wellbeing.dto.auth.UserLoginDto;
import com.aytaj.wellbeing.dto.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    void sendOtpRegistration(RegistrationOtpDto email);

    Boolean otpRegistrationVerification(RegisterRequest request);

    TokenResponse login(UserLoginDto dto);

    TokenResponse refreshToken(HttpServletRequest request);

    void logout(HttpServletRequest request);

}
