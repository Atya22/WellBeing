package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dto.RegisterRequest;
import com.aytaj.wellbeing.dto.RegistrationOtpDto;
import com.aytaj.wellbeing.dto.UserLoginDto;
import com.aytaj.wellbeing.dto.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    <T extends RegisterRequest, E> void registerUser(T request);

    void sendOtpRegistration(RegistrationOtpDto email);

    TokenResponse login(UserLoginDto dto);

    TokenResponse refreshToken(HttpServletRequest request);

    void logout(HttpServletRequest request);

}
