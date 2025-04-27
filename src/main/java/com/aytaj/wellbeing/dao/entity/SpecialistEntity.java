package com.aytaj.wellbeing.dao.entity;

import com.aytaj.wellbeing.service.LoginUser;
import com.aytaj.wellbeing.util.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistEntity implements LoginUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private Integer age;
    private Integer yearsOfExperience;
    private String areaOfExpertise;
    private Boolean documentVerified;

    @Enumerated(EnumType.STRING)
    private Role role;


}
