package com.aytaj.wellbeing.dto.auth;

import org.springframework.validation.annotation.Validated;

@Validated
public interface RegisterRequest {
    String getEmail();

    String getOtp();
}
