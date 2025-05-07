package com.aytaj.wellbeing.dao.entity;
import com.aytaj.wellbeing.util.enums.RequestStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ReservationRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ClientEntity client;

    @ManyToOne
    private SpecialistEntity specialist;

    private LocalDateTime startTime;

    private String description;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
}
