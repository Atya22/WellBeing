package com.aytaj.wellbeing.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "available_slot")
public class AvailableSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SpecialistEntity specialist;

    private LocalDateTime startTime;

    private boolean isBooked = false;
}
