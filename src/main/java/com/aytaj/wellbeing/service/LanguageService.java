package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dao.entity.Language;
import com.aytaj.wellbeing.dao.entity.TherapeuticMethod;
import com.aytaj.wellbeing.dao.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public List<Language> findAllByNames(List<String> names) {
        List<Language> found = languageRepository.findByNameIgnoreCase(names);
        if (found.size() != names.size()) {
            throw new IllegalArgumentException("One or more selected languages are not supported.");
        }
        return found;
    }
}
