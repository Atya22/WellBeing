package com.aytaj.wellbeing.service.Impl;

import com.aytaj.wellbeing.dto.*;
import com.aytaj.wellbeing.exception.InvalidPasswordException;
import com.aytaj.wellbeing.exception.UserNotFoundException;
import com.aytaj.wellbeing.exception.UserRegisteredBeforeException;
import com.aytaj.wellbeing.security.JwtUtil;
import com.aytaj.wellbeing.security.JwtVerifier;
import com.aytaj.wellbeing.security.PasswordUtil;
import com.aytaj.wellbeing.service.*;
import com.aytaj.wellbeing.util.enums.Purpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ClientHandler clientHandler;
    private final SpecialistHandler specialistHandler;
    private final OtpService otpService;
    private final PasswordUtil passwordUtil;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final JwtVerifier jwtVerifier;


    public void sendOtpRegistration(RegistrationOtpDto dto) {
        String email = dto.getEmail();
        if (clientHandler.existsByEmail(email) || specialistHandler.existsByEmail(email)) {
            throw new UserRegisteredBeforeException("User with email " + email + " already exists.");
        } else {
            otpService.sendOtp(dto, Purpose.REGISTRATION);
        }
    }

    @Override
    public <T extends RegisterRequest, E> void registerUser(T request) {

        UserHandler<T, E> handler = (UserHandler<T, E>) resolveHandler(request);

        if (otpService.verifyOtp(request.getEmail(), Purpose.REGISTRATION, request.getOtp())) {
            if (handler.existsByEmail(request.getEmail())) {
                throw new UserRegisteredBeforeException("User with email " + request.getEmail() + " already exists.");
            }

            E enity = handler.mapToEntity(request);
            handler.save(enity);
        }
    }

    @Override
    public String login(UserLoginDto dto) {
        String email = dto.getEmail();
        String rawPassword = dto.getPassword();

        LoginUser user = userService.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email:" + email));
        if (passwordUtil.verifyPassword(rawPassword, user.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", user.getRole().name());
            long expirationMillis = userService.getJwtExpirationByRole(user.getRole());

            try {
                return jwtUtil.generateToken(user.getEmail(), user.getId(), expirationMillis, claims);
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate token", e);
            }
        } else {
            throw new InvalidPasswordException("Password is incorrect");
        }
    }

    @Override
    public void logout(String token) {


    }

    @SuppressWarnings("unchecked")
    private <T extends RegisterRequest> UserHandler<T, ?> resolveHandler(T request) {
        if (request instanceof ClientRegisterRequest) {
            return (UserHandler<T, ?>) clientHandler;
        } else if (request instanceof SpecialistRegisterRequest) {
            return (UserHandler<T, ?>) specialistHandler;
        } else {
            throw new IllegalArgumentException("No handler for type: " + request.getClass().getSimpleName());
        }
    }
}
