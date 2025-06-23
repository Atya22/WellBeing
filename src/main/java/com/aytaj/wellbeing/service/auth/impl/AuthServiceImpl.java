package com.aytaj.wellbeing.service.auth.impl;

import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dto.TokenResponse;
import com.aytaj.wellbeing.dto.auth.*;
import com.aytaj.wellbeing.exception.*;
import com.aytaj.wellbeing.infrastructure.RedisService;
import com.aytaj.wellbeing.security.JwtUtil;
import com.aytaj.wellbeing.security.PasswordUtil;
import com.aytaj.wellbeing.security.RefreshTokenService;
import com.aytaj.wellbeing.service.user.Impl.ClientHandler;
import com.aytaj.wellbeing.service.user.Impl.SpecialistHandler;
import com.aytaj.wellbeing.service.auth.AuthService;
import com.aytaj.wellbeing.service.auth.LoginUser;
import com.aytaj.wellbeing.service.auth.OtpService;
import com.aytaj.wellbeing.service.user.UserHandler;
import com.aytaj.wellbeing.service.user.UserService;
import com.aytaj.wellbeing.util.enums.Purpose;
import com.aytaj.wellbeing.util.enums.Role;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ClientHandler clientHandler;
    private final SpecialistHandler specialistHandler;
    private final OtpService otpService;
    private final PasswordUtil passwordUtil;
    private final UserService userService;
    private final JwtUtil jwtUtil;
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
    public Boolean otpRegistrationVerification(RegisterRequest request) {
        UserHandler<?> handler = resolveHandler(request);
        if (otpService.verifyOtp(request.getEmail(), Purpose.REGISTRATION, request.getOtp())) {
            if (handler.existsByEmail(request.getEmail())) {
                throw new UserRegisteredBeforeException("User with email " + request.getEmail() + " already exists.");
            }
            return true;
        }
        return false;
    }

    @Override
    public TokenResponse login(UserLoginDto dto, HttpServletResponse response) {
        String email = dto.getEmail();
        String rawPassword = dto.getPassword();

        LoginUser user = userService.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email:" + email));

        if (user instanceof SpecialistEntity specialist) {
            if (!specialist.getApprovedByModerator()) {
                throw new UnapprovedSpecialistException("Your account is pending approval by a moderator.");
            }
        }

        if (passwordUtil.verifyPassword(rawPassword, user.getPassword())) {
            long accessTokenExpiration = userService.getJwtExpirationByRole(user.getRole());
            long refreshTokenExpiration = Duration.ofDays(7).toMillis();

            try {
                String accessToken = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole(), accessTokenExpiration);
                String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId(), user.getRole(), refreshTokenExpiration);

                refreshTokenService.storeRefreshTokenInRedis(user.getEmail(), user.getId(), refreshToken, refreshTokenExpiration);
                refreshTokenService.setRefreshTokenCookie(refreshToken, refreshTokenExpiration, response);

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
        String refreshToken = refreshTokenService.extractRefreshTokenFromCookies(request);

        try {
            SignedJWT jwt = SignedJWT.parse(refreshToken);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();

            if (!"refresh".equals(claims.getStringClaim("type"))) {
                throw new InvalidTokenException("Not a refresh token");
            }

            if (claims.getExpirationTime().before(new Date())) {
                throw new TokenExpiredException("Refresh token expired");
            }

            String email = claims.getStringClaim("email");
            String userId = claims.getSubject();

            String redisKey = "refresh:" + email + ":" + userId;
            String tokenInRedis = redisService.get(redisKey);
            if (tokenInRedis == null || !tokenInRedis.equals(refreshToken)) {
                throw new InvalidTokenException("Refresh token is not valid or already revoked");
            }

            LoginUser user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Role userRole = user.getRole();
            long accessExp = userService.getJwtExpirationByRole(userRole);

            String newAccessToken = jwtUtil.generateToken(user.getEmail(), user.getId(), userRole, accessExp);

            return new TokenResponse(newAccessToken, refreshToken);

        } catch (Exception e) {
            throw new RuntimeException("Could not refresh token", e);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = refreshTokenService.extractRefreshTokenFromCookies(request);

        try {
            SignedJWT jwt = SignedJWT.parse(refreshToken);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();

            if (!"refresh".equals(claims.getStringClaim("type"))) {
                throw new InvalidTokenException("Not a refresh token");
            }

            String email = claims.getStringClaim("email");
            long userId = Long.parseLong(claims.getSubject());

            String redisKey = "refresh:" + email + ":" + userId;
            redisService.delete(redisKey);

            refreshTokenService.deleteRefreshTokenCookie(response);

        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token", e);
        }
    }

    @SuppressWarnings("unchecked")
    private UserHandler<?> resolveHandler(RegisterRequest request) {
        if (request instanceof ClientRegistrationRequest) {
            return clientHandler;
        } else if (request instanceof SpecialistRegistrationRequest) {
            return specialistHandler;
        } else {
            throw new IllegalArgumentException("No handler for type: " + request.getClass().getSimpleName());
        }
    }

}
