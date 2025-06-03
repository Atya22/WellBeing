package com.aytaj.wellbeing.service.auth;

import com.aytaj.wellbeing.dao.entity.*;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dto.auth.SpecialistRegistrationRequest;
import com.aytaj.wellbeing.mapper.SpecialistMapper;
import com.aytaj.wellbeing.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistRegistrationService {
    private final SpecialistRepository specialistRepository;
    private final SpecialistMapper specialistMapper;
    private final DiplomaFileService diplomaFileService;
    private final LanguageService languageService;
    private final TherapeuticMethodService therapeuticMethodService;
    private final CertificateFileService certificateFileService;
    private final AuthService authService;

    @CacheEvict(value = "specialistPages", allEntries = true)
    public void registerSpecialist(
            SpecialistRegistrationRequest dto,
            MultipartFile diplomaFile,
            List<MultipartFile> certificateFiles
    ) {
        if (authService.otpRegistrationVerification(dto)) {

            List<LanguageEntity> languageEntities = languageService.findAllByNames(dto.getLanguages());
            List<TherapeuticMethodEntity> methods = therapeuticMethodService.findAllByNames(dto.getTherapeuticMethods());

            SpecialistEntity specialist = specialistMapper.dtoToEntity(dto, languageEntities, methods);

            specialistRepository.save(specialist);

            DiplomaEntity bachelorDiploma = diplomaFileService.storeDiploma(
                    diplomaFile,
                    dto.getBachelorDiploma(),
                    specialist);
            specialist.setDiplomas(List.of(bachelorDiploma));

            List<CertificateEntity> certificates = certificateFileService.storeAllCertificates(
                    certificateFiles,
                    dto.getCertificates(),
                    specialist);
            specialist.setCertificates(certificates);
        }
    }
}
