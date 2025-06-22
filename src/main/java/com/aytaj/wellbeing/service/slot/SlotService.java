package com.aytaj.wellbeing.service.slot;

import com.aytaj.wellbeing.dao.entity.AvailableSlotEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.repository.AvailableSlotRepository;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dto.CreateSlotRequest;
import com.aytaj.wellbeing.mapper.SlotMapper;
import com.aytaj.wellbeing.security.TokenUtils;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotService {
    private final SpecialistRepository specialistRepository;
    private final SlotMapper slotMapper;
    private final AvailableSlotRepository slotRepository;
    private final TokenUtils tokenUtils;

    public AvailableSlotEntity createSlot(HttpServletRequest servletRequest, CreateSlotRequest createSlotRequest){
        Long specialistId = tokenUtils.extractId(servletRequest);
        SpecialistEntity specialist = specialistRepository.findById(specialistId).orElseThrow(() -> new RuntimeException("Specialist not found"));
        return slotRepository.save(slotMapper.entityToDto(specialist, createSlotRequest));
    }
}
