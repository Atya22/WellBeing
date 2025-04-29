package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dto.*;

import java.text.ParseException;

public interface AuthService {

    <T extends RegisterRequest, E> void registerUser(T request);

    void sendOtpRegistration(RegistrationOtpDto email);

    TokenResponse login(UserLoginDto dto);

    TokenResponse refreshToken(String refreshToken);

    void logout(String token);

    String takeTokenFromHeader(String authHeader);
}
