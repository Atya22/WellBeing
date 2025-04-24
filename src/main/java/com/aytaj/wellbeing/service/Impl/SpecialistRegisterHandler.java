package com.aytaj.wellbeing.service.Impl;

import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.repository.ClientRepository;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dto.ClientRegisterRequest;
import com.aytaj.wellbeing.dto.SpecialistRegisterRequest;
import com.aytaj.wellbeing.mapper.ClientMapper;
import com.aytaj.wellbeing.mapper.SpecialistMapper;
import com.aytaj.wellbeing.service.UserRegisterHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SpecialistRegisterHandler implements UserRegisterHandler<SpecialistRegisterRequest, SpecialistEntity> {

    private final SpecialistRepository specialistRepository;
    private final SpecialistMapper specialistMapper;

    @Override
    public boolean existsByEmail(String email) {
        return specialistRepository.findByEmail(email).isPresent();
    }

    @Override
    public SpecialistEntity mapToEntity(SpecialistRegisterRequest request) {
        return specialistMapper.dtoToEntity(request);
    }

    @Override
    public void save(SpecialistEntity entity) {
        specialistRepository.save(entity);
    }

}
