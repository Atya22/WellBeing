package com.aytaj.wellbeing.dto.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotResponse {
    private String specialistFullName;

    private BigDecimal price;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
