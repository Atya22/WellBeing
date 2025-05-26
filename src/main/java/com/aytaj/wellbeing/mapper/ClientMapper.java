package com.aytaj.wellbeing.mapper;

import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.dao.repository.ClientRepository;
import com.aytaj.wellbeing.dto.ClientRegistrationRequest;
import com.aytaj.wellbeing.security.PasswordUtil;
import com.aytaj.wellbeing.util.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class ClientMapper {
    public final ClientRepository clientRepository;
    public final PasswordUtil passwordUtil;

    public ClientEntity dtoToEntity(ClientRegistrationRequest clientRegistrationRequest) {

        return ClientEntity.builder()
                .fullName(clientRegistrationRequest.getFullName())
                .password(passwordUtil.hashPassword(clientRegistrationRequest.getPassword()))
                .phoneNumber(clientRegistrationRequest.getPhoneNumber())
                .email(clientRegistrationRequest.getEmail())
                .age(clientRegistrationRequest.getAge())
                .role(Role.CLIENT)
                .registeredAt(LocalDate.now())
                .build();
    }
}
