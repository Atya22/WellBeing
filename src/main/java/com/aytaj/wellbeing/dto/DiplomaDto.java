package com.aytaj.wellbeing.dto;

import com.aytaj.wellbeing.util.enums.DegreeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiplomaDto {
    @NotBlank
    private DegreeType degreeLevel;

    @NotBlank
    private String diplomaNumber;

    @NotBlank
    private String universityName;

    @Past
    private LocalDate graduationDate;
}
