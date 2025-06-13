package com.aytaj.wellbeing.security;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.interfaces.RSAPublicKey;

@Component
@Getter
@RequiredArgsConstructor
public class JwtVerifier {
    private final KeyUtil keyUtil;
    public boolean verifyToken(String token) throws Exception{
        SignedJWT signedJWT = SignedJWT.parse(token);

        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) keyUtil.loadPublicKey());

        return signedJWT.verify(verifier);
    }
}
