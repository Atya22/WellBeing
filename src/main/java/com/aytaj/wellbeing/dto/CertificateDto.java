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

    @NotBlank
    private String title;

    @NotBlank
    private String issuedBy;

    @PastOrPresent
    private LocalDate certificationDate;

    @NotNull
    private MultipartFile certificateFile;
}
