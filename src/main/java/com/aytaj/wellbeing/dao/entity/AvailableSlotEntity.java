package com.aytaj.wellbeing.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "available_slot")
public class AvailableSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SpecialistEntity specialist;

    private BigDecimal price;

    private LocalDateTime startTime;

    private boolean isBooked = false;
}
