package com.aytaj.wellbeing.security;

import com.aytaj.wellbeing.exception.InvalidTokenException;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    public String extractBearerToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new InvalidTokenException("Authorization header must start with Bearer");
        }
        return header.substring(7);
    }
}
