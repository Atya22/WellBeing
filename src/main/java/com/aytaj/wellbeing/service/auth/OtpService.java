package com.aytaj.wellbeing.service.auth;

import com.aytaj.wellbeing.dto.auth.RegistrationOtpDto;
import com.aytaj.wellbeing.util.enums.Purpose;

public interface OtpService {
    String generateOtp(RegistrationOtpDto dto, Purpose purpose);

    void sendOtp(RegistrationOtpDto dto, Purpose purpose);

    boolean verifyOtp(String email,Purpose purpose, String otp);
}
