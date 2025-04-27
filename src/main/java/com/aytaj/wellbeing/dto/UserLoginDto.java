package com.aytaj.wellbeing.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    String email;
    String password;
}
