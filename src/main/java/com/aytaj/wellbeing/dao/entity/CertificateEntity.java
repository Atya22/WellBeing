package com.aytaj.wellbeing.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String fileName;
    private String issuedBy;
    private LocalDate certificationDate;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private SpecialistEntity specialist;
}
