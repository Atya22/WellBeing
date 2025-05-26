package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dao.entity.TherapeuticMethod;
import com.aytaj.wellbeing.dao.repository.TherapeuticMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TherapeuticMethodService {

    private final TherapeuticMethodRepository methodRepository;

    public List<TherapeuticMethod> findAllByNames(List<String> names) {
        List<TherapeuticMethod> found = methodRepository.findByNameInIgnoreCase(names);
        if (found.size() != names.size()) {
            throw new IllegalArgumentException("One or more therapeutic methods are not supported.");
        }
        return found;
    }
}
