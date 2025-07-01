package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.AvailableSlotEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dto.CreateSlotRequest;
import com.aytaj.wellbeing.dto.SlotDto;
import com.aytaj.wellbeing.dto.response.SlotResponse;
import org.springframework.stereotype.Component;

@Component
public class SlotMapper {
    public AvailableSlotEntity dtoToEntity(SpecialistEntity specialist, CreateSlotRequest request) {

        return AvailableSlotEntity.builder()
                .specialist(specialist)
                .price(request.getPrice())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }

    public SlotResponse toResponse(AvailableSlotEntity slotEntity) {
        return SlotResponse.builder()
                .specialistFullName(slotEntity.getSpecialist().getFullName())
                .price(slotEntity.getPrice())
                .startTime(slotEntity.getStartTime())
                .endTime(slotEntity.getEndTime())
                .build();
    }

    public SlotDto entityToDto(AvailableSlotEntity entity){
        return SlotDto.builder()
                .specialistId(entity.getSpecialist().getId())
                .price(entity.getPrice())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }
}
