package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.AvailableSlotEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dto.CreateSlotRequest;
import org.springframework.stereotype.Component;

@Component
public class SlotMapper {
    public AvailableSlotEntity entityToDto(SpecialistEntity specialist, CreateSlotRequest request){

        return AvailableSlotEntity.builder()
                .specialist(specialist)
                .price(request.getPrice())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }
}
