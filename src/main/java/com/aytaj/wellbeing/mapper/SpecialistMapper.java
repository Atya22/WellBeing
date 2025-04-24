package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dto.SpecialistRegisterRequest;
import com.aytaj.wellbeing.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpecialistMapper {
    public final SpecialistRepository specialistRepository;

    public SpecialistEntity dtoToEntity(SpecialistRegisterRequest request) {
        return SpecialistEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .age(request.getAge())
                .yearsOfExperience(request.getYearsOfExperience())
                .areaOfExpertise(request.getAreaOfExpertise())
                .documentVerified(false)  // default value
                .role(Role.SPECIALIST)
                .build();
    }

}
