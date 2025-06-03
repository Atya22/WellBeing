package com.aytaj.wellbeing.service.user;

import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dto.SpecialistDetailsDto;
import com.aytaj.wellbeing.dto.SpecialistSearchDto;
import com.aytaj.wellbeing.mapper.SpecialistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SpecialistService {
    private final SpecialistRepository specialistRepository;
    private final SpecialistMapper specialistMapper;

    @Cacheable(value = "specialistPages", key = "'page_' + #page + '_size_' + #size")
    public Page<SpecialistSearchDto> getSpecialists(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<SpecialistEntity> specialists = specialistRepository.findAll(pageable);
        return specialists.map(specialistMapper::entityToSearchDto);
    }

    public SpecialistDetailsDto getSpecialist(Long id) {
        SpecialistEntity specialist = specialistRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Specialist not found"));
        return specialistMapper.entityToDto(specialist);
    }
}
