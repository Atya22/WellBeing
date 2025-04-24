package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dto.ClientRegisterRequest;
import com.aytaj.wellbeing.dto.RegisterRequest;
import com.aytaj.wellbeing.dto.RegistrationOtpDto;

public interface AuthService {

    String login(String email, String password);

    void logout(String token);

    <T extends RegisterRequest, E> void registerUser(T request);

    void sendOtpRegistration(RegistrationOtpDto email);
}
