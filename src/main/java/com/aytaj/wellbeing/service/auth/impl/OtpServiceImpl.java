package com.aytaj.wellbeing.service.auth.impl;

import com.aytaj.wellbeing.dto.RegistrationOtpDto;
import com.aytaj.wellbeing.exception.InvalidOtpException;
import com.aytaj.wellbeing.infrastructure.EmailService;
import com.aytaj.wellbeing.service.auth.OtpService;
import com.aytaj.wellbeing.infrastructure.RedisService;
import com.aytaj.wellbeing.util.enums.Purpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final RedisService redisService;
    private final EmailService emailService;
    private static final long OTP_EXPIRATION_MINUTES = 120;

    @Override
    public String generateOtp(RegistrationOtpDto dto, Purpose purpose) {
        String otp = String.valueOf(new Random().nextInt(90000) + 10000);
        String key = dto.getEmail() + purpose;
        redisService.save(key, otp, OTP_EXPIRATION_MINUTES, TimeUnit.MINUTES);
        return otp;
    }

    @Override
    public void sendOtp(RegistrationOtpDto dto, Purpose purpose) {
        String otp = generateOtp(dto, purpose);
        String subject = "Your WellBeing OTP Code";
        String body = "Hello,\n\nYour verification code is: " + otp +
                "\nIt will expire in " + OTP_EXPIRATION_MINUTES + " minutes.";
//        emailService.sendEmail(dto.getEmail(), subject, body);
    }

    @Override
    public boolean verifyOtp(String email, Purpose purpose, String otp) {
        String key = email + purpose;
        String storedOtp = redisService.get(key);
        if (storedOtp == null) {
            throw new InvalidOtpException("OTP expired or not found. Please request a new one.");
        }
        if (!otp.equals(storedOtp)) {
            throw new InvalidOtpException("Invalid OTP. Please check the code and try again.");
        }
        redisService.delete(email);
        return true;
    }
}
