package com.aytaj.wellbeing.dao.repository;

import com.aytaj.wellbeing.dao.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {
    List<LanguageEntity> findByNameInIgnoreCase(List<String> names);

}
