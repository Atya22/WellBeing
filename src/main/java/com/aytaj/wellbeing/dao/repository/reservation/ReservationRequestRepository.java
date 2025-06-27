package com.aytaj.wellbeing.dao.repository.reservation;

import com.aytaj.wellbeing.dao.entity.ReservationRequestEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.util.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRequestRepository extends JpaRepository<ReservationRequestEntity, Long> {
    List<ReservationRequestEntity> findBySpecialistAndStatus(SpecialistEntity specialist, RequestStatus status);
    ReservationRequestEntity findAllById(Long id);
}
