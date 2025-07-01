package com.aytaj.wellbeing.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotDto {
    private Long specialistId;

    private BigDecimal price;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}

