package com.aytaj.wellbeing.controller;

import com.aytaj.wellbeing.dto.SpecialistDetailsDto;
import com.aytaj.wellbeing.dto.SpecialistSearchDto;
import com.aytaj.wellbeing.dto.SpecialistUpdateRequest;
import com.aytaj.wellbeing.service.user.SpecialistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/specialists")
@RequiredArgsConstructor
public class SpecialistController {
    private final SpecialistService specialistService;

    @GetMapping
    public ResponseEntity<Page<SpecialistSearchDto>> getSpecialists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<SpecialistSearchDto> specialists = specialistService.getSpecialists(page, size);
        return ResponseEntity.ok(specialists);
    }

    @GetMapping("/{id}")
    public SpecialistDetailsDto getSpecialist(@PathVariable Long id) {
        return specialistService.getSpecialist(id);
    }

    @PutMapping
    public ResponseEntity<SpecialistDetailsDto> updateSpecialist(
            @RequestBody @Valid SpecialistUpdateRequest updateRequest,
            HttpServletRequest request
    ) {
        SpecialistDetailsDto updated = specialistService.updateSpecialist(request, updateRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id.toString() == authentication.name and hasRole('SPECIALIST') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        specialistService.deleteCurrentSpecialistAccount(id);
        return ResponseEntity.noContent().build();
    }
}
