package com.aytaj.wellbeing.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.validation.Valid;

import java.util.List;

@Getter
@Setter
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class SpecialistUpdateRequest {

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
}
