package com.aytaj.wellbeing.service.auth;

import com.aytaj.wellbeing.util.enums.Role;

public interface LoginUser {
    String getEmail();
    String getPassword();
    Role getRole();
    Long getId();
}
