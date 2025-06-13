package com.aytaj.wellbeing.service.user;

import com.aytaj.wellbeing.dao.entity.LanguageEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.entity.TherapeuticMethodEntity;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dto.SpecialistDetailsDto;
import com.aytaj.wellbeing.dto.SpecialistSearchDto;
import com.aytaj.wellbeing.dto.SpecialistUpdateRequest;
import com.aytaj.wellbeing.mapper.SpecialistMapper;
import com.aytaj.wellbeing.security.TokenUtils;
import com.aytaj.wellbeing.service.LanguageService;
import com.aytaj.wellbeing.service.TherapeuticMethodService;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SpecialistService {
    private final SpecialistRepository specialistRepository;
    private final SpecialistMapper specialistMapper;
    private final LanguageService languageService;
    private final TherapeuticMethodService therapeuticMethodService;
    private final TokenUtils tokenUtils;

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

    @Transactional
    public SpecialistDetailsDto updateSpecialist(HttpServletRequest request, SpecialistUpdateRequest updateRequest) {
        Long id = tokenUtils.extractId(request);
        SpecialistEntity specialist = specialistRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Specialist not found"));
        specialist.setPhoneNumber(updateRequest.getPhoneNumber());
        specialist.setAge(updateRequest.getAge());
        specialist.setYearsOfExperience(updateRequest.getYearsOfExperience());
        specialist.setAreaOfExpertise(updateRequest.getAreaOfExpertise());

        List<LanguageEntity> languageEntities = languageService.findAllByNames(updateRequest.getLanguages());
        List<TherapeuticMethodEntity> therapeuticMethodEntities = therapeuticMethodService.findAllByNames(updateRequest.getTherapeuticMethods());

        specialist.setLanguages(languageEntities);
        specialist.setTherapeuticMethods(therapeuticMethodEntities);

        specialistRepository.save(specialist);
        return specialistMapper.entityToDto(specialist);
    }

    public void deleteCurrentSpecialistAccount(Long id) {
        specialistRepository.deleteById(id);
    }
}
