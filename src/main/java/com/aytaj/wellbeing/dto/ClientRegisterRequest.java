package com.aytaj.wellbeing.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientRegisterRequest implements RegisterRequest {
    @NotNull(message = "Full name is required")
    @Size(min = 3, message = "Full name should have at least 3 characters")
    private String fullName;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Age is required")
    private Integer age;

    @NotNull(message = "OTP is required")
    @Pattern(regexp = "\\d{5}", message = "OTP must be a 5-digit number")
    private String otp;

}

