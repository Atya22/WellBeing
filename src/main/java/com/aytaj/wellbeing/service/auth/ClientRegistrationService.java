package com.aytaj.wellbeing.service.auth;

import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.repository.ClientRepository;
import com.aytaj.wellbeing.dto.ClientRegistrationRequest;
import com.aytaj.wellbeing.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientRegistrationService {
    private final AuthService authService;
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    public void registerClient(ClientRegistrationRequest request) {
        if(authService.otpRegistrationVerification(request)){

            ClientEntity entity = clientMapper.dtoToEntity(request);
            clientRepository.save(entity);
        }
    }
}
