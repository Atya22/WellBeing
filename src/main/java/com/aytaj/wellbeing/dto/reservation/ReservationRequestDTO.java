package com.aytaj.wellbeing.dto.reservation;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

@Getter
@Setter
@Builder
public class ReservationRequestDTO {
    private Long specialistId;
    private Long slotId;
    private String description;
    private String paymentIntentId;
}