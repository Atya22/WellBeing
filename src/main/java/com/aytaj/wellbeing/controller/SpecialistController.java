package com.aytaj.wellbeing.controller;
import com.aytaj.wellbeing.dto.SpecialistDetailsDto;
import com.aytaj.wellbeing.dto.SpecialistSearchDto;
import com.aytaj.wellbeing.service.user.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping("/{id}")
    @GetMapping
    public SpecialistDetailsDto getSpecialist(@PathVariable Long id){
        return specialistService.getSpecialist(id);
    }
}
