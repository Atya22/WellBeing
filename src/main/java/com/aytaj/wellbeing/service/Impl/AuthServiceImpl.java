package com.aytaj.wellbeing.service.Impl;

import com.aytaj.wellbeing.dto.*;
import com.aytaj.wellbeing.dto.TokenResponse;
import com.aytaj.wellbeing.exception.*;
import com.aytaj.wellbeing.infrastructure.RedisService;
import com.aytaj.wellbeing.security.JwtUtil;
import com.aytaj.wellbeing.security.JwtVerifier;
import com.aytaj.wellbeing.security.PasswordUtil;
import com.aytaj.wellbeing.security.RefreshTokenService;
import com.aytaj.wellbeing.service.*;
import com.aytaj.wellbeing.util.enums.Purpose;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
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
    private final RedisService redisService;
    private final RefreshTokenService refreshTokenService;


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
    public TokenResponse login(UserLoginDto dto) {
        String email = dto.getEmail();
        String rawPassword = dto.getPassword();

        LoginUser user = userService.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email:" + email));

        if (passwordUtil.verifyPassword(rawPassword, user.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", user.getRole().name());

            long accessTokenExpiration = userService.getJwtExpirationByRole(user.getRole());
            long refreshTokenExpiration = Duration.ofDays(7).toMillis();

            try {
                String accessToken = jwtUtil.generateToken(user.getEmail(), user.getId(), accessTokenExpiration, claims);
                String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId(), refreshTokenExpiration);

                redisService.set("refresh:" + user.getId(), refreshToken, refreshTokenExpiration);
                refreshTokenService.storeRefreshToken(user.getEmail(), user.getId(), refreshToken, refreshTokenExpiration);

                return new TokenResponse(accessToken, refreshToken);

            } catch (Exception e) {
                throw new RuntimeException("Failed to generate token", e);
            }
        } else {
            throw new InvalidPasswordException("Password is incorrect");
        }
    }

    @Override
    public TokenResponse refreshToken(HttpServletRequest request) {
        String refreshToken = getToken(request);
        try {
            SignedJWT jwt = SignedJWT.parse(refreshToken);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();

            if (!"refresh".equals(claims.getStringClaim("type"))) {
                throw new InvalidTokenException("Not a refresh token");
            }


            if (claims.getExpirationTime().before(new Date())) {
                throw new TokenExpiredException("Refresh token expired");
            }

            String email = claims.getSubject();

            String redisKey = "refresh:" + email;
            String tokenInRedis = redisService.get(redisKey);
            if (tokenInRedis == null || !tokenInRedis.equals(refreshToken)) {
                throw new InvalidTokenException("Refresh token is not valid or already revoked");
            }

            LoginUser user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Map<String, Object> newClaims = Map.of("role", user.getRole().name());
            long accessExp = userService.getJwtExpirationByRole(user.getRole());

            String newAccessToken = jwtUtil.generateToken(user.getEmail(), user.getId(), accessExp, newClaims);

            return new TokenResponse(newAccessToken, refreshToken);

        } catch (Exception e) {
            throw new RuntimeException("Could not refresh token", e);
        }
    }


    @SuppressWarnings("unchecked")
    private <T extends RegisterRequest> UserHandler<T, ?> resolveHandler(T request) {
        if (request instanceof ClientRegistrationRequest) {
            return (UserHandler<T, ?>) clientHandler;
        } else if (request instanceof SpecialistRegistrationRequest) {
            return (UserHandler<T, ?>) specialistHandler;
        } else {
            throw new IllegalArgumentException("No handler for type: " + request.getClass().getSimpleName());
        }
    }


    @Override
    public void logout(HttpServletRequest request) {
        String refreshToken = getToken(request);

        try {
            SignedJWT jwt = SignedJWT.parse(refreshToken);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();

            if (!"refresh".equals(claims.getStringClaim("type"))) {
                throw new InvalidTokenException("Not a refresh token");
            }

            String email = claims.getSubject();
            Long userId = claims.getLongClaim("id");

            String redisKey = "refresh:" + email + ":" + userId;
            redisService.delete(redisKey);

        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token", e);
        }
    }

    public String getToken(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");

        if (token == null) {
            throw new InvalidTokenException("No token found in request attributes");
        }
        return token;
    }

}
