package com.aytaj.wellbeing.dao.repository;


import com.aytaj.wellbeing.dao.entity.AvailableSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlotEntity, Long> {
    Optional<AvailableSlotEntity> findAllById(Long id);
}
