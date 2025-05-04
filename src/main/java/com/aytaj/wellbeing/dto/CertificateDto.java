package com.aytaj.wellbeing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Issued by is required")
    private String issuedBy;

    @PastOrPresent(message = "Certification date cannot be in the future")
    private LocalDate certificationDate;

    @NotNull
    private MultipartFile certificateFile;
}
