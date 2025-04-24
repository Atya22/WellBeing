package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dto.RegisterRequest;

public interface UserRegisterHandler<T extends RegisterRequest, E> {
    boolean existsByEmail(String email);

    E mapToEntity(T request);

    void save(E entity);

}
