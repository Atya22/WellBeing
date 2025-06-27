package com.aytaj.wellbeing.service.user;

import com.aytaj.wellbeing.dao.entity.*;
import com.aytaj.wellbeing.dao.repository.AvailableSlotRepository;
import com.aytaj.wellbeing.dao.repository.SpecialistRepository;
import com.aytaj.wellbeing.dao.repository.reservation.ReservationRequestRepository;
import com.aytaj.wellbeing.dto.SpecialistDetailsDto;
import com.aytaj.wellbeing.dto.SpecialistSearchDto;
import com.aytaj.wellbeing.dto.SpecialistUpdateRequest;
import com.aytaj.wellbeing.mapper.SpecialistMapper;
import com.aytaj.wellbeing.security.TokenUtils;
import com.aytaj.wellbeing.service.LanguageService;
import com.aytaj.wellbeing.service.TherapeuticMethodService;
import com.aytaj.wellbeing.service.payment.PaymentService;
import com.aytaj.wellbeing.util.enums.RequestStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SpecialistService {
    private final SpecialistRepository specialistRepository;
    private final SpecialistMapper specialistMapper;
    private final LanguageService languageService;
    private final TherapeuticMethodService therapeuticMethodService;
    private final TokenUtils tokenUtils;
    private final ReservationRequestRepository reservationRequestRepository;
    private final PaymentService paymentService;
    private final AvailableSlotRepository availableSlotRepository;

    @Cacheable(value = "specialistPages", key = "'page_' + #page + '_size_' + #size")
    public Page<SpecialistSearchDto> getSpecialists(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<SpecialistEntity> specialists = specialistRepository.findAll(pageable);
        return specialists.map(specialistMapper::entityToSearchDto);
    }

    public SpecialistDetailsDto getSpecialist(Long id) {
        SpecialistEntity specialist = specialistRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Specialist not found"));
        return specialistMapper.entityToDto(specialist);
    }

    public SpecialistDetailsDto updateSpecialist(HttpServletRequest request, SpecialistUpdateRequest updateRequest) {
        Long id = tokenUtils.extractId(request);
        SpecialistEntity specialist = specialistRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Specialist not found"));
        specialist.setPhoneNumber(updateRequest.getPhoneNumber());
        specialist.setAge(updateRequest.getAge());
        specialist.setYearsOfExperience(updateRequest.getYearsOfExperience());
        specialist.setAreaOfExpertise(updateRequest.getAreaOfExpertise());

        List<LanguageEntity> languageEntities = languageService.findAllByNames(updateRequest.getLanguages());
        List<TherapeuticMethodEntity> therapeuticMethodEntities = therapeuticMethodService.findAllByNames(updateRequest.getTherapeuticMethods());

        specialist.setLanguages(languageEntities);
        specialist.setTherapeuticMethods(therapeuticMethodEntities);

        specialistRepository.save(specialist);
        return specialistMapper.entityToDto(specialist);
    }

    public void deleteCurrentSpecialistAccount(Long id) {
        specialistRepository.deleteById(id);
    }

    @Transactional
    public void approveReservation(Long id) {
        ReservationRequestEntity reservation = reservationRequestRepository.findAllById(id);
        if (!reservation.getStatus().equals(RequestStatus.PENDING)) {
            throw new IllegalStateException("Reservation already processed");
        }

        try {
            paymentService.capturePayment(reservation.getPaymentIntentId());
        } catch (Exception e) {
            log.error("Failed to capture payment for reservation {}: {}", reservation.getId(), e.getMessage());
            throw new RuntimeException("Payment capture failed. Cannot approve reservation.");
        }
        AvailableSlotEntity slot = reservation.getSlot();

        reservation.setStatus(RequestStatus.APPROVED);
        reservation.setPaymentCaptured(true);
        slot.setBooked(true);
        reservationRequestRepository.save(reservation);
        availableSlotRepository.save(slot);
    }

    @Transactional
    public void denyReservation(Long id) {
        ReservationRequestEntity reservation = reservationRequestRepository.findAllById(id);

        if (!reservation.getStatus().equals(RequestStatus.PENDING)) {
            throw new IllegalStateException("Reservation already processed");
        }

        try {
            paymentService.cancelPayment(reservation.getPaymentIntentId());
        } catch (Exception e) {
            log.warn("Failed to cancel PaymentIntent for reservation {}: {}", reservation.getId(), e.getMessage());
            throw new RuntimeException("Payment capture failed. Cannot deny reservation.");
        }
        reservation.setStatus(RequestStatus.REJECTED);
        reservationRequestRepository.save(reservation);
    }
}
