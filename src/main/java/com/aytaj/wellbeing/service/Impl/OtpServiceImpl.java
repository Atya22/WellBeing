package com.aytaj.wellbeing.service.Impl;

import com.aytaj.wellbeing.dto.RegistrationOtpDto;
import com.aytaj.wellbeing.service.OtpService;
import com.aytaj.wellbeing.util.enums.Purpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final RedisService redisService;
    private final EmailServiceImpl emailService;
    private static final long OTP_EXPIRATION_MINUTES = 5;

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
        emailService.sendEmail(dto.getEmail(), subject, body);
    }

    @Override
    public boolean verifyOtp(String email, Purpose purpose, String otp) {
        String key = email + purpose;
        String storedOtp = redisService.get(key);
        var isEqual = otp.equals(storedOtp);
        redisService.delete(email);
        return isEqual;
    }
}
