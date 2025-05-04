package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dto.RegisterRequest;

import java.util.Optional;

public interface UserHandler <E> {
    boolean existsByEmail(String email);

    Optional<E> findByEmail(String email);

//    E mapToEntity(T request);

    void save(E entity);

}
