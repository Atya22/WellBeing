package com.aytaj.wellbeing.dao.repository;

import com.aytaj.wellbeing.dao.entity.TherapeuticMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TherapeuticMethodRepository extends JpaRepository<TherapeuticMethod, Long> {
    List<TherapeuticMethod> findByNameInIgnoreCase (List<String> names);
}
