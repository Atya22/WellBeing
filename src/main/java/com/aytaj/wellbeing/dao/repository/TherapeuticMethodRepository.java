package com.aytaj.wellbeing.dao.repository;

import com.aytaj.wellbeing.dao.entity.TherapeuticMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TherapeuticMethodRepository extends JpaRepository<TherapeuticMethodEntity, Long> {
    List<TherapeuticMethodEntity> findByNameInIgnoreCase (List<String> names);
}
