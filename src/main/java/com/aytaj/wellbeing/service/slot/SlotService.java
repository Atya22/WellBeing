package com.aytaj.wellbeing.service.slot;

import com.aytaj.wellbeing.dao.entity.AvailableSlotEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.repository.AvailableSlotRepository;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dto.CreateSlotRequest;
import com.aytaj.wellbeing.dto.SlotDto;
import com.aytaj.wellbeing.dto.response.SlotResponse;
import com.aytaj.wellbeing.mapper.SlotMapper;
import com.aytaj.wellbeing.security.TokenUtils;
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

    public SlotResponse createSlot(HttpServletRequest servletRequest, CreateSlotRequest createSlotRequest){
        Long specialistId = tokenUtils.extractId(servletRequest);
        SpecialistEntity specialist = specialistRepository.findById(specialistId).orElseThrow(() -> new RuntimeException("Specialist not found"));
        AvailableSlotEntity savedSlot = slotRepository.save(slotMapper.dtoToEntity(specialist, createSlotRequest));
        return slotMapper.toResponse(savedSlot);
    }

    public SlotDto getSlot(Long id){
       AvailableSlotEntity slotEntity =  slotRepository.findAllById(id).orElseThrow(
                () -> new RuntimeException("Slot not found"));
       return slotMapper.entityToDto(slotEntity);
    }
}
