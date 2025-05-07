package com.aytaj.wellbeing.controller;


import com.aytaj.wellbeing.dto.resevation.ReservationRequestDTO;
import com.aytaj.wellbeing.service.reservation.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
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
}
