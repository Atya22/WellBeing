package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.repository.ClientRepository;
import com.aytaj.wellbeing.dto.ClientRegisterRequest;
import com.aytaj.wellbeing.util.enums.Role;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientMapper {
    public final ClientRepository clientRepository;

    public ClientEntity dtoToEntity(ClientRegisterRequest clientRegisterRequest) {

        return ClientEntity.builder()
                .fullName(clientRegisterRequest.getFullName())
                .password(clientRegisterRequest.getPassword())
                .phoneNumber(clientRegisterRequest.getPhoneNumber())
                .email(clientRegisterRequest.getEmail())
                .age(clientRegisterRequest.getAge())
                .role(Role.CLIENT)
                .build();
    }
}
