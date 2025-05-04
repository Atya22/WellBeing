package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.CertificateEntity;
import com.aytaj.wellbeing.dao.entity.DiplomaEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dto.CertificateDto;
import com.aytaj.wellbeing.dto.DiplomaDto;
import org.springframework.stereotype.Component;

import java.security.cert.Certificate;

@Component
public class CertificateMapper {

    public CertificateEntity dtoToEntity(CertificateDto dto,
                                         String fileName,
                                         String filePath,
                                         SpecialistEntity specialist) {
        return CertificateEntity.builder()
                .title(dto.getTitle())
                .issuedBy(dto.getIssuedBy())
                .certificationDate(dto.getCertificationDate())
                .fileName(fileName)
                .filePath(filePath)
                .specialist(specialist)
                .build();
    }
}
