package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dto.ClientRegisterRequest;
import com.aytaj.wellbeing.dto.RegisterRequest;
import com.aytaj.wellbeing.dto.RegistrationOtpDto;
import com.aytaj.wellbeing.dto.UserLoginDto;

public interface AuthService {

    String login (UserLoginDto dto);

    void logout(String token);

    <T extends RegisterRequest, E> void registerUser(T request);

    void sendOtpRegistration(RegistrationOtpDto email);
}
