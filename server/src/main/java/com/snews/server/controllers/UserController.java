package com.snews.server.controllers;

import com.snews.server.dto.LoginDto;
import com.snews.server.dto.RegisterDto;
import com.snews.server.dto.UserDto;
import com.snews.server.entities.User;
import com.snews.server.entities.UserRole;
import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.services.userRole.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    //    todo remove after adding the real user service
    private final UserRoleService userRoleService;

    public UserController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping(path = "/login")
    public UserDto login(@RequestBody LoginDto loginDto) {
        UserDto userdto = new UserDto();
        userdto.setUsername("haibusa2005")
                .setRoles(Set.of(
                        this.userRoleService.getUserRole(UserRoleEnum.USER).getRole().name().toLowerCase(),
                        this.userRoleService.getUserRole(UserRoleEnum.MODERATOR).getRole().name().toLowerCase()
                ));

        return userdto;
    }

    @PostMapping(path = "/register")
    public User register(@RequestBody RegisterDto registerDto) {
        User user = new User();
        UserRole role = this.userRoleService.getUserRole(UserRoleEnum.USER);
        user.setUsername("haibusa2005")
                .setEmail("haibusa2005@abv.bg")
                .setUserRole(Set.of(role));
        return user;
    }
}
