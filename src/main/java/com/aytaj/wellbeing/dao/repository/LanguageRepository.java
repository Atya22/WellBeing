package com.aytaj.wellbeing.dao.repository;

import com.aytaj.wellbeing.dao.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findByNameIgnoreCase(List<String> names);;
}
