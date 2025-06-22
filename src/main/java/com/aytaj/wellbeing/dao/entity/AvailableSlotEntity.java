package com.aytaj.wellbeing.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@Table(name = "available_slot")
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SpecialistEntity specialist;

    private BigDecimal price;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean isBooked = false;
}
