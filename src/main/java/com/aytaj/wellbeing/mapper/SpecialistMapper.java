package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.LanguageEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.entity.TherapeuticMethodEntity;
import com.aytaj.wellbeing.dto.LanguageDto;
import com.aytaj.wellbeing.dto.SpecialistSearchDto;
import com.aytaj.wellbeing.dto.TherapeuticMethodDto;
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

    public SpecialistEntity dtoToEntity(SpecialistRegistrationRequest request, List<LanguageEntity> languageEntities, List<TherapeuticMethodEntity> methods) {
        return SpecialistEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordUtil.hashPassword(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .age(request.getAge())
                .areaOfExpertise(request.getAreaOfExpertise())
                .yearsOfExperience(request.getYearsOfExperience())
                .languageEntities(languageEntities)
                .therapeuticMethodEntities(methods)
                .role(Role.SPECIALIST)
                .documentVerified(false)
                .approvedByModerator(false)
                .registeredAt(LocalDate.now())
                .build();
    }

    public SpecialistSearchDto entityToSearchDto(SpecialistEntity entity) {
        List<LanguageDto> languages = entity.getLanguageEntities().stream()
                .map(languageMapper::toDto)
                .toList();

        List<TherapeuticMethodDto> therapeuticMethods = entity.getTherapeuticMethodEntities().stream()
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
