package com.aytaj.wellbeing.controller;

import com.aytaj.wellbeing.dto.CreateSlotRequest;
import com.aytaj.wellbeing.service.slot.SlotService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/specialists/slots")
@RequiredArgsConstructor
public class SlotController {
private final SlotService slotService;

@PreAuthorize("hasRole('SPECIALIST')")
@PostMapping("/create")
    public ResponseEntity<?> createSlot(HttpServletRequest servletRequest,
                                        @RequestBody CreateSlotRequest createSlotRequest){
    return ResponseEntity.ok(slotService.createSlot(servletRequest, createSlotRequest));
}
}
