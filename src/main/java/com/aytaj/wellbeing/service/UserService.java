package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dto.RegisterRequest;

public interface UserService<T extends RegisterRequest> {
    void register(T request);
}
