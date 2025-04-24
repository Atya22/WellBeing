package com.aytaj.wellbeing.service.Impl;

import com.aytaj.wellbeing.dto.ClientRegisterRequest;
import com.aytaj.wellbeing.dto.RegisterRequest;
import com.aytaj.wellbeing.dto.RegistrationOtpDto;
import com.aytaj.wellbeing.dto.SpecialistRegisterRequest;
import com.aytaj.wellbeing.exception.UserRegisteredBeforeException;
import com.aytaj.wellbeing.service.AuthService;
import com.aytaj.wellbeing.service.OtpService;
import com.aytaj.wellbeing.service.UserRegisterHandler;
import com.aytaj.wellbeing.util.enums.Purpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ClientRegisterHandler clientHandler;
    private final SpecialistRegisterHandler specialistHandler;
    private final OtpService otpService;


    //
    public void sendOtpRegistration(RegistrationOtpDto email) {
        otpService.sendOtp(email, Purpose.REGISTRATION);
    }


    @Override
    public <T extends RegisterRequest, E> void registerUser(T request) {

        UserRegisterHandler<T, E> handler = (UserRegisterHandler<T, E>) resolveHandler(request);

        if (otpService.verifyOtp(request.getEmail(),Purpose.REGISTRATION, request.getOtp())) {
            if (handler.existsByEmail(request.getEmail())) {
                throw new UserRegisteredBeforeException("User with email " + request.getEmail() + " already exists.");
            }

            E enity = handler.mapToEntity(request);
            handler.save(enity);
        }
    }


    @SuppressWarnings("unchecked")
    private <T extends RegisterRequest> UserRegisterHandler<T, ?> resolveHandler(T request) {
        if (request instanceof ClientRegisterRequest) {
            return (UserRegisterHandler<T, ?>) clientHandler;
        } else if (request instanceof SpecialistRegisterRequest) {
            return (UserRegisterHandler<T, ?>) specialistHandler;
        } else {
            throw new IllegalArgumentException("No handler for type: " + request.getClass().getSimpleName());
        }
    }

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public void logout(String token) {

    }
}
