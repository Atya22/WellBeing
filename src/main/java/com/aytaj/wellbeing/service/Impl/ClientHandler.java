package com.aytaj.wellbeing.service.Impl;

import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.repository.ClientRepository;
import com.aytaj.wellbeing.dto.ClientRegistrationRequest;
import com.aytaj.wellbeing.mapper.ClientMapper;
import com.aytaj.wellbeing.service.UserHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ClientHandler implements UserHandler<ClientRegistrationRequest, ClientEntity> {
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
    public ClientEntity mapToEntity(ClientRegistrationRequest request) {
        return clientMapper.dtoToEntity(request);
    }

    @Override
    public void save(ClientEntity entity) {
        clientRepository.save(entity);
    }

}
