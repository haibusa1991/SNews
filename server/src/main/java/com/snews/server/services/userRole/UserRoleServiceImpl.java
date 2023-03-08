package com.snews.server.services.userRole;

import com.snews.server.entities.UserRoleEntity;
import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.repositories.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRoleServiceImpl implements UserRoleService {


    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public UserRoleEntity getUserRole(UserRoleEnum userRoleEnum) {
        return this.userRoleRepository.getUserRoleByRole(userRoleEnum);
    }

    @Override
    public void initRoles() {
        if (this.userRoleRepository.count() > 0) return;
        this.userRoleRepository.saveAll(
                Arrays.stream(UserRoleEnum.values())
                        .map(UserRoleEntity::new)
                        .toList());
    }


}
