package com.aytaj.wellbeing.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialistRegistrationRequest implements RegisterRequest {
    @NotNull(message = "Full name is required")
    @Size(min = 3, message = "Full name should have at least 3 characters")
    private String fullName;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;

    @Pattern(regexp = "\\+?[0-9]{7,15}")
    private String phoneNumber;

    @Min(21)
    @Max(100)
    private Integer age;

    @Min(0)
    @Max(80)
    private Integer yearsOfExperience;

    @NotBlank
    private String areaOfExpertise;

    private List<String> languages;

    private List<String> therapeuticMethods;

    @Valid
    @NotNull
    private DiplomaDto bachelorDiploma;

//    @Valid
//    private List<DiplomaDto> additionalDiplomas;

    @Valid
    private List<CertificateDto> certificates;

    @NotNull(message = "OTP is required")
    @Pattern(regexp = "\\d{5}", message = "OTP must be a 5-digit number")
    private String otp;
}
