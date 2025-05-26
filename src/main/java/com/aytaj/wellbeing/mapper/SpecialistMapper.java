package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.LanguageEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.entity.TherapeuticMethod;
import com.aytaj.wellbeing.dto.SpecialistRegistrationRequest;
import com.aytaj.wellbeing.security.PasswordUtil;
import com.aytaj.wellbeing.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SpecialistMapper {
    public final PasswordUtil passwordUtil;

    public SpecialistEntity dtoToEntity(SpecialistRegistrationRequest request, List<LanguageEntity> languageEntities, List<TherapeuticMethod> methods) {
        return SpecialistEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordUtil.hashPassword(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .age(request.getAge())
                .areaOfExpertise(request.getAreaOfExpertise())
                .yearsOfExperience(request.getYearsOfExperience())
                .languageEntities(languageEntities)
                .therapeuticMethods(methods)
                .role(Role.SPECIALIST)
                .documentVerified(false)
                .approvedByModerator(false)
                .registeredAt(LocalDate.now())
                .build();
    }

}
