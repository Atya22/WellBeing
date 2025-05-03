package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.DiplomaEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dto.DiplomaDto;
import com.aytaj.wellbeing.dto.SpecialistRegistrationRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class DiplomaMapper {
    public DiplomaEntity DtoToEntity(DiplomaDto dto, String fileName, String filePath, SpecialistEntity specialist) {
        return DiplomaEntity.builder()
                .degreeLevel(dto.getDegreeLevel())
                .diplomaNumber(dto.getDiplomaNumber())
                .universityName(dto.getUniversityName())
                .graduationDate(dto.getGraduationDate())
                .fileName(fileName)
                .filePath(filePath)
                .specialist(specialist)
                .build();
    }
}
