package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dao.entity.*;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dto.SpecialistRegistrationRequest;
import com.aytaj.wellbeing.mapper.SpecialistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistAuthService {
    private final SpecialistRepository specialistRepository;
    private final SpecialistMapper specialistMapper;
    private final DiplomaFileService diplomaFileService;
    private final LanguageService languageService;
    private final TherapeuticMethodService therapeuticMethodService;
    private final CertificateFileService certificateFileService;


    public void registerSpecialist(
            SpecialistRegistrationRequest dto,
            MultipartFile diplomaFile,
            List<MultipartFile> certificateFiles
    ) {

        List<Language> languages = languageService.findAllByNames(dto.getLanguages());
        List<TherapeuticMethod> methods = therapeuticMethodService.findAllByNames(dto.getTherapeuticMethods());

        SpecialistEntity specialist = specialistMapper.dtoToEntity(dto, languages, methods);

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

        specialistRepository.save(specialist);

    }
}
