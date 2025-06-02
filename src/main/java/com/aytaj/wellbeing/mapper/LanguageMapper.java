package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.LanguageEntity;
import com.aytaj.wellbeing.dto.LanguageDto;
import org.springframework.stereotype.Component;

@Component
public class LanguageMapper {

    public LanguageDto toDto(LanguageEntity langEntity) {
        return LanguageDto.builder()
                .name(langEntity.getName())
                .build();
    }
}
