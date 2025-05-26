package com.aytaj.wellbeing.service.user.Impl;

import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.repository.ClientRepository;
import com.aytaj.wellbeing.mapper.ClientMapper;
import com.aytaj.wellbeing.service.user.UserHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ClientHandler implements UserHandler<ClientEntity> {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public boolean existsByEmail(String email) {

        return clientRepository.findByEmail(email).isPresent();
    }

    @Override
    public Optional<ClientEntity> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }


    @Override
    public void save(ClientEntity entity) {
        clientRepository.save(entity);
    }

}
