package com.aytaj.wellbeing.security;

import com.aytaj.wellbeing.exception.InvalidTokenException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;

@Component
@RequiredArgsConstructor
public class TokenUtils {
    private final JwtVerifier jwtVerifier;

    public String extractBearerToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new InvalidTokenException("Authorization header must start with Bearer");
        }
        return header.substring(7);
    }

    public Long extractUserId(String token) {
        try {
            JWTClaimsSet claims = extractAllClaims(token);
            return ((Number) claims.getClaim("id")).longValue();
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: cannot extract ID");
        }
    }

    public String extractUserRole(String token) {
        try {
            JWTClaimsSet claims = extractAllClaims(token);
            return (String) claims.getClaim("role");
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: cannot extract role");
        }
    }

    public String extractEmail(String token) {
        try {
            JWTClaimsSet claims = extractAllClaims(token);
            return claims.getSubject(); // email записан в subject
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: cannot extract email");
        }
    }

    private JWTClaimsSet extractAllClaims(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        if (jwtVerifier.verifyToken(token)) {
            return signedJWT.getJWTClaimsSet();
        } else {
            throw new InvalidTokenException("JWT signature verification failed");
        }
    }

}
