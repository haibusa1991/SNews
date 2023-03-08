package com.snews.server.controllers;

import com.snews.server.dto.RegisterDto;
import com.snews.server.dto.UserDto;
import com.snews.server.services.user.UserService;
import com.snews.server.services.userRole.UserRoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserRoleService userRoleService;
    private final UserService userService;

    public UserController(
            UserRoleService userRoleService, UserService userService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
    }


    @PostMapping(path = "/register")
    public String register(@RequestBody RegisterDto registerDto) {
        return "ok";
    }


//    @PostMapping(path = "/register")
//    public UserDto register(@RequestBody @Valid RegisterDto registerDto,
//                            BindingResult bindingResult) throws UserAlreadyRegisteredException, MalformedDataException {
//
//        if (bindingResult.hasErrors()) {
//            String errorMessages = bindingResult
//                    .getAllErrors()
//                    .stream()
//                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                    .collect(Collectors.joining(System.lineSeparator()));
//            throw new MalformedDataException(errorMessages);
//        }
//
//        String candidateUsername = registerDto.getUsername();
//        UserEntity userEntityByUsername = this.userService.getUserByUsername(candidateUsername);
//        if (userEntityByUsername != null && candidateUsername.equalsIgnoreCase(userEntityByUsername.getUsername())) {
//            throw new UserAlreadyRegisteredException("Username already registered.");
//        }
//
//        String candidateEmail = registerDto.getEmail();
//        UserEntity userEntityByEmail = this.userService.getUserByEmail(candidateEmail);
//        if (userEntityByEmail != null && candidateEmail.equalsIgnoreCase(userEntityByEmail.getEmail())) {
//            throw new UserAlreadyRegisteredException("Email address is already registered.");
//        }
//
//        return this.userService.registerUser(registerDto);
//
//    }

    @GetMapping("/username")
    public String getUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @GetMapping("/user")
    public UserDto getUser(){
        UserDto userDto = new UserDto();

        userDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        Set<String> roles = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(e-> e.replaceAll("ROLE_",""))
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        userDto.setRoles(roles);

        return userDto;

    }

}
