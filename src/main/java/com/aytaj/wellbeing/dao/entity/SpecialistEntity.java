package com.aytaj.wellbeing.dao.entity;

import com.aytaj.wellbeing.service.auth.LoginUser;
import com.aytaj.wellbeing.util.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "specialist")
public class SpecialistEntity implements LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;
    private String phoneNumber;
    private Integer age;
    private Integer yearsOfExperience;
    private String areaOfExpertise;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "specialist_languages",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<LanguageEntity> languages;

    @ManyToMany
    @JoinTable(
            name = "specialist_methods",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "method_id")
    )
    private List<TherapeuticMethodEntity> therapeuticMethods;


    private Boolean documentVerified = false;

    private Boolean approvedByModerator = false;

//    approved by who?

    private LocalDate registeredAt;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiplomaEntity> diplomas;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificateEntity> certificates;
}
