package com.aytaj.wellbeing.dao.repository;

import com.aytaj.wellbeing.dao.entity.ReservationEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.util.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findBySpecialistAndStatus(SpecialistEntity specialist, RequestStatus status);
    ReservationEntity findAllById(Long id);
}
