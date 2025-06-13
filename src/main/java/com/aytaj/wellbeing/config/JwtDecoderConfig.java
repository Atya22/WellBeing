package com.aytaj.wellbeing.config;

import com.aytaj.wellbeing.security.JwtVerifier;
import com.aytaj.wellbeing.security.KeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

@RequiredArgsConstructor
@Configuration
public class JwtDecoderConfig {
    private final KeyUtil keyUtil;

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        PublicKey publicKey = keyUtil.loadPublicKey();
        return NimbusJwtDecoder.withPublicKey((RSAPublicKey) publicKey).build();
    }
}
