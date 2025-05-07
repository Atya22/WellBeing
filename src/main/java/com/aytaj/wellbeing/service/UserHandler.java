package com.aytaj.wellbeing.service;

import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.entity.SpecialistEntity;
import com.aytaj.wellbeing.dto.RegisterRequest;

import java.util.Optional;

public interface UserHandler<E> {
    boolean existsByEmail(String email);

    Optional<E> findByEmail(String email);

    void save(E entity);

}
