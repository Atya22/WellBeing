package com.aytaj.wellbeing.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegistrationOtpDto {
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
