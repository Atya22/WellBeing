package com.aytaj.wellbeing.dao.entity;

import com.aytaj.wellbeing.service.LoginUser;
import com.aytaj.wellbeing.util.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class ClientEntity implements LoginUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private Integer age;
    private Boolean isVerified;

    private LocalDate registeredAt;

    @Enumerated(EnumType.STRING)
    private Role role;

}
