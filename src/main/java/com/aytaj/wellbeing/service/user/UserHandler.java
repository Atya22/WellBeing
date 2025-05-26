package com.aytaj.wellbeing.service.user;

import java.util.Optional;

public interface UserHandler<E> {
    boolean existsByEmail(String email);

    Optional<E> findByEmail(String email);

    void save(E entity);

}
