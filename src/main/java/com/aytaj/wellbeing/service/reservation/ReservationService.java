package com.aytaj.wellbeing.service.reservation;

import com.aytaj.wellbeing.dao.entity.AvailableSlotEntity;
import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.entity.ReservationEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dao.repository.ClientRepository;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dao.repository.AvailableSlotRepository;
import com.aytaj.wellbeing.dao.repository.ReservationRepository;
import com.aytaj.wellbeing.dto.reservation.ReservationRequestDTO;
import com.aytaj.wellbeing.dto.reservation.ReservationResponseDto;
import com.aytaj.wellbeing.mapper.ReservationMapper;
import com.aytaj.wellbeing.security.TokenUtils;
import com.aytaj.wellbeing.util.enums.RequestStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final TokenUtils tokenUtils;
    private final ClientRepository clientRepository;
    private final SpecialistRepository specialistRepository;
    private final AvailableSlotRepository availableSlotRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;


    @Transactional
    public void requestReservation(ReservationRequestDTO dto, HttpServletRequest request) {
        Long clientId = tokenUtils.extractId(request);
        ClientEntity client = clientRepository.findById(clientId).orElseThrow(
                () -> new RuntimeException("Client not found"));

        SpecialistEntity specialist = specialistRepository.findById(dto.getSpecialistId()).orElseThrow(
                () -> new RuntimeException("Specialist not found"));

        AvailableSlotEntity slot = availableSlotRepository.findAllById(dto.getSlotId()).orElseThrow(
                () -> new RuntimeException("Slot not found"));
        if (slot.isBooked()) {
            throw new RuntimeException("Slot is already booked");
        }

        ReservationEntity requestEntity = new ReservationEntity();
        requestEntity.setClient(client);
        requestEntity.setSpecialist(specialist);
        requestEntity.setSlot(slot);
        requestEntity.setDescription(dto.getDescription());
        requestEntity.setStatus(RequestStatus.PENDING);
        requestEntity.setCreatedAt(LocalDateTime.now());
        requestEntity.setPaymentIntentId(dto.getPaymentIntentId());

        reservationRepository.save(requestEntity);
    }

    public List<ReservationResponseDto> getPendingRequests(HttpServletRequest request) {
        Long specialistId = tokenUtils.extractId(request);

        SpecialistEntity specialist = specialistRepository.findById(specialistId).orElseThrow(
                () -> new RuntimeException("Specialist not found")
        );

        return reservationRepository.findBySpecialistAndStatus(specialist, RequestStatus.PENDING)
                .stream()
                .map(reservationMapper::entityToDtoResponse)
                .collect(Collectors.toList());
    }
}

