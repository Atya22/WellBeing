package com.aytaj.wellbeing.dao.repository.reservation;

import com.aytaj.wellbeing.dao.entity.ReservationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRequestRepository extends JpaRepository<ReservationRequestEntity, Long> {

}
