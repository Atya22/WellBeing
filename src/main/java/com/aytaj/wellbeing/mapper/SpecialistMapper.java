package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.*;
import com.aytaj.wellbeing.dto.*;
import com.aytaj.wellbeing.dto.auth.SpecialistRegistrationRequest;
import com.aytaj.wellbeing.security.PasswordUtil;
import com.aytaj.wellbeing.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpecialistMapper {
    public final PasswordUtil passwordUtil;
    public final LanguageMapper languageMapper;
    public final TherapeuticMethodMapper methodMapper;
    public final DiplomaMapper diplomaMapper;
    public final CertificateMapper certificateMapper;

    public SpecialistEntity dtoToEntity(SpecialistRegistrationRequest request, List<LanguageEntity> languageEntities, List<TherapeuticMethodEntity> methods) {
        return SpecialistEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordUtil.hashPassword(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .age(request.getAge())
                .areaOfExpertise(request.getAreaOfExpertise())
                .yearsOfExperience(request.getYearsOfExperience())
                .languages(languageEntities)
                .therapeuticMethods(methods)
                .role(Role.SPECIALIST)
                .documentVerified(false)
                .approvedByModerator(false)
                .registeredAt(LocalDate.now())
                .build();
    }

    public SpecialistDetailsDto entityToDto(SpecialistEntity specialist) {
        List<LanguageDto> languages = specialist.getLanguages().stream()
                .map(languageMapper::toDto)
                .toList();

        List<TherapeuticMethodDto> therapeuticMethods = specialist.getTherapeuticMethods().stream()
                .map(methodMapper::toDto)
                .toList();

        List<CertificateDto> certificates = specialist.getCertificates().stream()
                .map(certificateMapper::entityToDo)
                .toList();

        List<DiplomaDto> diplomas = specialist.getDiplomas().stream()
                .map(diplomaMapper::entityToDto)
                .toList();

        return SpecialistDetailsDto.builder()
                .fullName(specialist.getFullName())
                .phoneNumber(specialist.getPhoneNumber())
                .yearsOfExperience(specialist.getYearsOfExperience())
                .areaOfExpertise(specialist.getAreaOfExpertise())
                .languages(languages)
                .therapeuticMethods(therapeuticMethods)
                .certificates(certificates)
                .diplomas(diplomas)
                .build();
    }

    public SpecialistSearchDto entityToSearchDto(SpecialistEntity entity) {
        List<LanguageDto> languages = entity.getLanguages().stream()
                .map(languageMapper::toDto)
                .toList();

        List<TherapeuticMethodDto> therapeuticMethods = entity.getTherapeuticMethods().stream()
                .map(methodMapper::toDto)
                .toList();

        return SpecialistSearchDto.builder()
                .fullName(entity.getFullName())
                .areaOfExpertise(entity.getAreaOfExpertise())
                .yearsOfExperience(entity.getYearsOfExperience())
                .languages(languages)
                .therapeuticMethods(therapeuticMethods)
                .build();
    }
}
