package com.snews.server.configuration;

import com.snews.server.dto.UserDto;
import com.snews.server.entities.UserEntity;
import com.snews.server.entities.UserRoleEntity;
import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.repositories.UserRoleRepository;
import com.snews.server.services.user.UserService;
import com.snews.server.services.userRole.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Component
public class DatabaseInitialization implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;

    public DatabaseInitialization(UserRoleService userRoleService, UserService userService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.initRoles();
        this.userService.initUsers();
    }


}
