package com.aytaj.wellbeing.dao.repository;

import com.aytaj.wellbeing.dao.entity.DiplomaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiplomaRepository extends JpaRepository<DiplomaEntity, Long> {
}
