package com.aytaj.wellbeing.dao.repository;

import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistEntity, Long> {
    Optional<SpecialistEntity> findByEmail(String email);
}
