package com.aytaj.wellbeing.controller;


import com.aytaj.wellbeing.dao.entity.ReservationRequestEntity;
import com.aytaj.wellbeing.dto.reservation.ReservationRequestDTO;
import com.aytaj.wellbeing.service.reservation.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor

public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> requestSession(@RequestBody ReservationRequestDTO dto,
                                            HttpServletRequest request
    ) {
        reservationService.requestReservation(dto, request);
        return ResponseEntity.ok("Reservation request submitted.");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ReservationRequestEntity>> getPending(HttpServletRequest request) {
        List<ReservationRequestEntity> requests = reservationService.getPendingRequests(request);
        return ResponseEntity.ok(requests);
    }
}
