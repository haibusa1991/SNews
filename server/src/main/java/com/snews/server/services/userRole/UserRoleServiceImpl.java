package com.snews.server.services.userRole;

import com.snews.server.entities.UserRole;
import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.repositories.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRoleServiceImpl implements UserRoleService {


    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @PostConstruct
    private void initRoles() {
        if (this.userRoleRepository.count() > 0) return;
        this.userRoleRepository.saveAll(
                Arrays.stream(UserRoleEnum.values())
                        .map(UserRole::new)
                        .toList());

    }

    @Override
    public UserRole getUserRole(UserRoleEnum userRoleEnum) {
        return this.userRoleRepository.getUserRoleByRole(userRoleEnum);
    }


}
