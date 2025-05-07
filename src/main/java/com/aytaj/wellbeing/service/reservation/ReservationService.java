package com.aytaj.wellbeing.service.reservation;

import ch.qos.logback.core.net.server.Client;
import com.aytaj.wellbeing.dao.entity.AvailableSlotEntity;
import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.entity.ReservationRequestEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.repository.ClientRepository;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dao.repository.reservation.AvailableSlotRepository;
import com.aytaj.wellbeing.dao.repository.reservation.ReservationRequestRepository;
import com.aytaj.wellbeing.dto.resevation.ReservationRequestDTO;
import com.aytaj.wellbeing.security.TokenUtils;
import com.aytaj.wellbeing.util.enums.RequestStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final TokenUtils tokenUtils;
    private final ClientRepository clientRepository;
    private final SpecialistRepository specialistRepository;
    private final AvailableSlotRepository availableSlotRepository;
    private final ReservationRequestRepository reservationRequestRepository;

    public void requestReservation(ReservationRequestDTO dto, HttpServletRequest request) {
        String clientEmail = tokenUtils.extractEmail(request);
        ClientEntity client = clientRepository.findByEmail(clientEmail).orElseThrow(
                () -> new RuntimeException("Client not found"));

        SpecialistEntity specialist = specialistRepository.findById(dto.getSpecialistId()).orElseThrow(
                () -> new RuntimeException("Specialist not found"));

        AvailableSlotEntity slot = availableSlotRepository.findAllById(dto.getSlotId()).orElseThrow(
                () -> new RuntimeException("Slot not found"));
        if (slot.isBooked()) {
            throw new RuntimeException("Slot is already booked");
        }

        ReservationRequestEntity requestEntity = new ReservationRequestEntity();
        requestEntity.setClient(client);
        requestEntity.setSpecialist(specialist);
        requestEntity.setSlot(slot);
        requestEntity.setDescription(dto.getDescription());
        requestEntity.setStatus(RequestStatus.PENDING);

        reservationRequestRepository.save(requestEntity);
    }

    public List<ReservationRequestEntity> getPendingRequests(HttpServletRequest request) {
        String specialistEmail = tokenUtils.extractEmail(request);
        SpecialistEntity specialist = specialistRepository.findByEmail(specialistEmail).orElseThrow(
                () -> new RuntimeException("Specialist not found")
        );
        return reservationRequestRepository.findBySpecialistAndStatus(specialist, RequestStatus.PENDING);
    }
}

