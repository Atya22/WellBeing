package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dao.entity.DiplomaEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.repository.DiplomaRepository;
import com.aytaj.wellbeing.dto.DiplomaDto;
import com.aytaj.wellbeing.mapper.DiplomaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DiplomaFileService {
    private final DiplomaRepository diplomaRepository;
    private final DiplomaMapper diplomaMapper;

    private final Path diplomaStoragePath = Paths.get("uploads/diplomas");

    public DiplomaEntity storeDiploma(MultipartFile file, DiplomaDto dto, SpecialistEntity specialist) {
        try {
            Files.createDirectories(diplomaStoragePath);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = diplomaStoragePath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            DiplomaEntity diploma = diplomaMapper.dtoToEntity(dto, fileName, filePath.toString(), specialist);

            return diplomaRepository.save(diploma);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store diploma", e);
        }
    }

    public Resource loadDiplomaFile(String fileName) {
        try {
            Path filePath = diplomaStoragePath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Diploma file not found: " + fileName);
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new RuntimeException("Could not read the diploma file.", e);
        }
    }
}
