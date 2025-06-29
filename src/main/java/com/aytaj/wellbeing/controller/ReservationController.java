package com.aytaj.wellbeing.controller;


import com.aytaj.wellbeing.dto.reservation.ReservationRequestDTO;
import com.aytaj.wellbeing.dto.reservation.ReservationResponseDto;
import com.aytaj.wellbeing.service.reservation.ReservationService;
import com.aytaj.wellbeing.service.user.SpecialistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor

public class ReservationController {
    private final ReservationService reservationService;
    private final SpecialistService specialistService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> requestSession(@RequestBody ReservationRequestDTO dto,
                                            HttpServletRequest request
    ) {
        reservationService.requestReservation(dto, request);
        return ResponseEntity.ok("Reservation request submitted.");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ReservationResponseDto>> getPending(HttpServletRequest request) {
        List<ReservationResponseDto> requests = reservationService.getPendingRequests(request);
        return ResponseEntity.ok(requests);
    }


    @PostMapping("/approve/{id}")
    @PreAuthorize("hasRole('SPECIALIST')")
    public ResponseEntity<String> approveReservation(@PathVariable Long id) {
        specialistService.approveReservation(id);
        return ResponseEntity.ok("Reservation approved and payment captured.");
    }

    @PostMapping("/deny/{id}")
    @PreAuthorize("hasRole('SPECIALIST')")
    public ResponseEntity<String> denyReservation(@PathVariable Long id) {
        specialistService.denyReservation(id);
        return ResponseEntity.ok("Reservation and payment cancelled.");
    }
}
