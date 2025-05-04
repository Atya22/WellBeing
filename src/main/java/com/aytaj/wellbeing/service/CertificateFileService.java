package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dao.entity.CertificateEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.repository.CertificateRepository;
import com.aytaj.wellbeing.dto.CertificateDto;
import com.aytaj.wellbeing.mapper.CertificateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.cert.Certificate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificateFileService {

    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    private final Path certStoragePath = Paths.get("uploads/certificates");

    public CertificateEntity storeCertificate(MultipartFile file,
                                              CertificateDto dto,
                                              SpecialistEntity specialist) {
        try {
            Files.createDirectories(certStoragePath);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = certStoragePath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            CertificateEntity certificate = certificateMapper.dtoToEntity(dto,
                    fileName,
                    filePath.toString(),
                    specialist);

            return certificateRepository.save(certificate);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store certificate", e);
        }
    }

    public List<CertificateEntity> storeAllCertificates(
            List<MultipartFile> files,
            List<CertificateDto> dtos,
            SpecialistEntity specialist) {
        {
            if (files.size() != dtos.size()) {
                throw new IllegalArgumentException("Mismatch between number of files and DTOs");
            }

            List<CertificateEntity> certificates = new ArrayList<>();

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                CertificateDto dto = dtos.get(i);
                certificates.add(storeCertificate(file, dto, specialist));
            }

            return certificates;
        }
    }
}
