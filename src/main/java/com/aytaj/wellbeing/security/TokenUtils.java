package com.aytaj.wellbeing.security;

import com.aytaj.wellbeing.exception.InvalidTokenException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
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

    public String extractEmail(HttpServletRequest request) {
        try {
            JWTClaimsSet claims = extractAllClaims(request);
            return ((String) claims.getClaim("email"));
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: cannot extract email");
        }
    }

    public String extractRole(HttpServletRequest request) {
        try {
            JWTClaimsSet claims = extractAllClaims(request);
            return (String) claims.getClaim("role");
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: cannot extract role");
        }
    }

    public Long extractId(HttpServletRequest request) {
        try {
            JWTClaimsSet claims = extractAllClaims(request);
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: cannot extract ID");
        }
    }

    private JWTClaimsSet extractAllClaims(HttpServletRequest request) throws Exception {
        String token = extractBearerToken(request.getHeader("Authorization"));
        SignedJWT signedJWT = SignedJWT.parse(token);
        if (jwtVerifier.verifyToken(token)) {
            return signedJWT.getJWTClaimsSet();
        } else {
            throw new InvalidTokenException("JWT signature verification failed");
        }
    }
}
