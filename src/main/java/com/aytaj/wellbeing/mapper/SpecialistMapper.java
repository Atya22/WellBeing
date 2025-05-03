package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.Language;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.entity.TherapeuticMethod;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
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

    public SpecialistEntity dtoToEntity(SpecialistRegistrationRequest request, List<Language> languages, List<TherapeuticMethod> methods) {
        return SpecialistEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordUtil.hashPassword(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .age(request.getAge())
                .yearsOfExperience(request.getYearsOfExperience())
                .areaOfExpertise(request.getAreaOfExpertise())
                .languages(languages)
                .therapeuticMethods(methods)
                .role(Role.SPECIALIST)
                .documentVerified(false)
                .approvedByModerator(false)
                .registeredAt(LocalDate.now())
                .build();
    }

}
