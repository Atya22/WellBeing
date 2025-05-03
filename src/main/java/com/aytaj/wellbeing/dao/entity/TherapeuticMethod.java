package com.aytaj.wellbeing.dao.entity;

import jakarta.persistence.*;

@Entity
public class TherapeuticMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}
