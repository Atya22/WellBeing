package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dao.entity.LanguageEntity;
import com.aytaj.wellbeing.dao.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public List<LanguageEntity> findAllByNames(List<String> names) {
        List<LanguageEntity> found = languageRepository.findByNameInIgnoreCase(names);
        if (found.size() != names.size()) {
            throw new IllegalArgumentException("One or more selected languageEntities are not supported.");
        }
        return found;
    }
}
