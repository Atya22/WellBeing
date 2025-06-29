package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.AvailableSlotEntity;
import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.entity.ReservationEntity;
import com.aytaj.wellbeing.dto.reservation.ReservationResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
   public ReservationResponseDto entityToDtoResponse(ReservationEntity requestEntity) {
        ClientEntity client = requestEntity.getClient();
        AvailableSlotEntity slot = requestEntity.getSlot();
        return ReservationResponseDto.builder()
                .clientFullName(client.getFullName())
                .clientAge(client.getAge())
                .clientEmail(client.getEmail())
                .clientPhoneNumber(client.getPhoneNumber())
                .description(requestEntity.getDescription())
                .slotStartTime(slot.getStartTime())
                .slotEndTime(slot.getEndTime())
                .status(requestEntity.getStatus())
                .requestCreatedAt(requestEntity.getCreatedAt())
                .build();
    }
}
