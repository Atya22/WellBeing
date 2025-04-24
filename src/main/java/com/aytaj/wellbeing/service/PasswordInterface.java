package com.aytaj.wellbeing.service;

public interface PasswordInterface {
    void initiatePasswordReset(String email);

    boolean resetPassword(String token, String newPassword);

    boolean validatePassword(String password);
}
