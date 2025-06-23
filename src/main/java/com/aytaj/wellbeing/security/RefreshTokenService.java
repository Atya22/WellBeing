package com.aytaj.wellbeing.security;

import com.aytaj.wellbeing.exception.InvalidTokenException;
import com.aytaj.wellbeing.infrastructure.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisService redisService;

    public void storeRefreshTokenInRedis(String email, Long id, String token, long refreshTokenExpiration) {
        String redisKey = "refresh:" + email + ":" + id;
        redisService.set(redisKey, token, refreshTokenExpiration);
    }

    public Optional<String> getRefreshTokenFromRedis(String email) {
        return Optional.ofNullable(redisService.get(email));
    }

    public void deleteRefreshTokenFromRedis(String email) {
        redisService.delete(email);
    }

    public void setRefreshTokenCookie(String refreshToken, Long refreshTokenExpiration, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");    // Ensure available for all paths or restrict as needed
        cookie.setMaxAge((int) (refreshTokenExpiration / 1000));
        response.addCookie(cookie);
    }

    public String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        throw new InvalidTokenException("Refresh token cookie is missing");
    }

    public void deleteRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

