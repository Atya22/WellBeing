package com.aytaj.wellbeing.service.auth;

import com.aytaj.wellbeing.dto.RegisterRequest;
import com.aytaj.wellbeing.dto.RegistrationOtpDto;
import com.aytaj.wellbeing.dto.UserLoginDto;
import com.aytaj.wellbeing.dto.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    void sendOtpRegistration(RegistrationOtpDto email);

    Boolean otpRegistrationVerification(RegisterRequest request);

    TokenResponse login(UserLoginDto dto);

    TokenResponse refreshToken(HttpServletRequest request);

    void logout(HttpServletRequest request);

}
