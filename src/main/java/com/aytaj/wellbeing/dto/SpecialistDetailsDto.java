package com.aytaj.wellbeing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialistDetailsDto {
    private String fullName;
    private String phoneNumber;
    private Integer yearsOfExperience;
    private String areaOfExpertise;
    private List<LanguageDto> languages;
    private List<TherapeuticMethodDto> therapeuticMethods;
    private List<DiplomaDto> diplomas;
    private List<CertificateDto> certificates;
}
