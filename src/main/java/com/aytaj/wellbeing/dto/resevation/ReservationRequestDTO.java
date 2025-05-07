package com.aytaj.wellbeing.dto.resevation;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class ReservationRequestDTO {
    private Long specialistId;
    private Long slotId;
    private String description;
}