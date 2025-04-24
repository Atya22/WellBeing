package com.aytaj.wellbeing.service.Impl;

import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.repository.ClientRepository;
import com.aytaj.wellbeing.dto.ClientRegisterRequest;
import com.aytaj.wellbeing.mapper.ClientMapper;
import com.aytaj.wellbeing.service.UserRegisterHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientRegisterHandler implements UserRegisterHandler<ClientRegisterRequest, ClientEntity> {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.findByEmail(email).isPresent();
    }

    @Override
    public ClientEntity mapToEntity(ClientRegisterRequest request) {
        return clientMapper.dtoToEntity(request);
    }

    @Override
    public void save(ClientEntity entity) {
        clientRepository.save(entity);
    }

}
