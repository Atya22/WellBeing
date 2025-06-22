package com.aytaj.wellbeing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSlotRequest {
    private BigDecimal price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
