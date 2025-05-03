package com.aytaj.wellbeing.dao.entity;

import com.aytaj.wellbeing.util.enums.DegreeType;
import jakarta.persistence.*;
import lombok.*;

import java.nio.file.Path;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DiplomaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DegreeType degreeLevel;

    private String diplomaNumber;
    private String universityName;
    private LocalDate graduationDate;
    private String fileName;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private SpecialistEntity specialist;
}
