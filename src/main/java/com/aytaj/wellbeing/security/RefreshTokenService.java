package com.aytaj.wellbeing.security;

import com.aytaj.wellbeing.infrastructure.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisService redisService;

    public void storeRefreshToken(String email, Long id, String token, long refreshTokenExpiration) {
        String redisKey = "refresh:" + email + ":" + id;
        redisService.set(redisKey, token, refreshTokenExpiration);
    }

    public Optional<String> getRefreshToken(String email) {
        return Optional.ofNullable(redisService.get(email));
    }

    public void deleteRefreshToken(String email) {
        redisService.delete(email);
    }
}

