package com.aytaj.wellbeing.dao.repository;

import com.aytaj.wellbeing.dao.entity.TherapeuticMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TherapeuticMethodRepository extends JpaRepository<TherapeuticMethod, Long> {
    List<TherapeuticMethod> findByNameIgnoreCase (List<String> names);
}
