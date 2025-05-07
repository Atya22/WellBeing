package com.aytaj.wellbeing.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
public class AvailableSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SpecialistEntity specialist;

    private LocalDateTime startTime;

    private boolean isBooked = false;
}
