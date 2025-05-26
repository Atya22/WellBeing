package com.aytaj.wellbeing.service.user;

import com.aytaj.wellbeing.dao.entity.ClientEntity;
import com.aytaj.wellbeing.service.user.Impl.ClientHandler;
import com.aytaj.wellbeing.service.user.Impl.SpecialistHandler;
import com.aytaj.wellbeing.service.auth.LoginUser;
import com.aytaj.wellbeing.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ClientHandler clientHandler;
    private final SpecialistHandler specialistHandler;


    public Optional<? extends LoginUser> findUserByEmail(String email) {
        Optional<ClientEntity> client = clientHandler.findByEmail(email);
        if (client.isPresent()) {
            return client;
        }

        return specialistHandler.findByEmail(email);

    }

    public long getJwtExpirationByRole(Role role) {
        return switch (role) {
            case CLIENT -> 2 * 60 * 60 * 1000L; // 2 hours
            case ADMIN -> 30 * 60 * 1000L; // 30  minutes
            default -> 60 * 60 * 1000L; // 1 hour
        };
    }
//    public void deleteUser()
}
