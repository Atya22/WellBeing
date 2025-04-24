package com.aytaj.wellbeing.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(String email, String subject, String message);
}
