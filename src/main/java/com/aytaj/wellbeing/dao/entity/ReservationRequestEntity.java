package com.aytaj.wellbeing.dao.entity;

import com.aytaj.wellbeing.util.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reservation_request")
public class ReservationRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ClientEntity client;

    @ManyToOne
    private SpecialistEntity specialist;

    @ManyToOne
    private AvailableSlotEntity slot;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String paymentIntentId;

    private boolean paymentCaptured;

}
