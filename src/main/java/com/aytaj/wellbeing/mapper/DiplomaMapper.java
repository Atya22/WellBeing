package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.DiplomaEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dto.DiplomaDto;
import org.springframework.stereotype.Component;

@Component
public class DiplomaMapper {
    public DiplomaEntity dtoToEntity(DiplomaDto dto, String fileName, String filePath, SpecialistEntity specialist) {
        return DiplomaEntity.builder()
                .degreeLevel(dto.getDegreeLevel())
                .diplomaNumber(dto.getDiplomaNumber())
                .universityName(dto.getUniversityName())
                .graduationYear(dto.getGraduationYear())
                .fileName(fileName)
                .filePath(filePath)
                .specialist(specialist)
                .build();
    }
}
