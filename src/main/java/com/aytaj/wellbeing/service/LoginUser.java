package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.util.enums.Role;

public interface LoginUser {
    String getEmail();
    String getPassword();
    Role getRole();
    Long getId();
}
