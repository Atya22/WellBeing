package com.aytaj.wellbeing.service;

public interface PasswordService {
    void initiatePasswordReset(String email);

    boolean resetPassword(String token, String newPassword);

    boolean validatePassword(String password);
}
