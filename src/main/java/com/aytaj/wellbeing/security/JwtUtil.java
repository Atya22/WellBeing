package com.aytaj.wellbeing.security;

import com.aytaj.wellbeing.util.enums.Role;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;


@AllArgsConstructor
@Component
public class JwtUtil {
    private final KeyUtil keyUtil;

    public String generateToken(String email, Long id, Role role, long expirationMillis) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("roles", List.of(role.name()));
        JWSSigner signer = new RSASSASigner(keyUtil.loadPrivateKey());
        JWTClaimsSet.Builder claimSet = new JWTClaimsSet.Builder()
                .subject(id.toString())
                .issueTime(new Date(System.currentTimeMillis()))
                .expirationTime(new Date(System.currentTimeMillis() + expirationMillis));

        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            claimSet.claim(key, value);
        }

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.RS256),
                claimSet.build());

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }


    public String generateRefreshToken(String email, Long id, Role role, Long refreshTokenExpiration) throws Exception {
        JWSSigner signer = new RSASSASigner(keyUtil.loadPrivateKey());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(id.toString())
                .expirationTime(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .claim("type", "refresh")
                .claim("email", email)
                .claim("roles", List.of(role.name()))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
}
