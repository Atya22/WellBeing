package com.aytaj.wellbeing.dto.reservation;


import com.aytaj.wellbeing.util.enums.RequestStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDto {
    private String clientFullName;
    private String clientEmail;
    private String clientPhoneNumber;
    private Integer clientAge;
    private LocalDateTime slotStartTime;
    private LocalDateTime slotEndTime;
    private String description;
    private LocalDateTime requestCreatedAt;
    private RequestStatus status;
}
