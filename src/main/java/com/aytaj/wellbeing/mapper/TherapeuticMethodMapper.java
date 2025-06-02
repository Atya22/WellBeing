package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.TherapeuticMethodEntity;
import com.aytaj.wellbeing.dto.TherapeuticMethodDto;
import org.springframework.stereotype.Component;

@Component
public class TherapeuticMethodMapper {

    public TherapeuticMethodDto toDto(TherapeuticMethodEntity methodEntity) {
        return TherapeuticMethodDto.builder()
                .methodName(methodEntity.getName())
                .build();
    }
}
