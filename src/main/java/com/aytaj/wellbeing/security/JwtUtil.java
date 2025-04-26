package com.aytaj.wellbeing.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import org.springframework.stereotype.Component;

import java.nio.file.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;

@Component
public class JwtUtil {

    public static String generateToken(String subject, long expirationMillis, Map<String, Object> claims) throws Exception {
        JWSSigner signer = new RSASSASigner(loadPrivateKey());
        JWTClaimsSet.Builder claimSet = new JWTClaimsSet.Builder()
                .subject(subject)
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


    private static PrivateKey loadPrivateKey() throws Exception {
        String key = Files.readString(Path.of("src/main/resources/keys/private.pem"))
                .replaceAll("-----\\w+ PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePrivate(spec);
    }
}
