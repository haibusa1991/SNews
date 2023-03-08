package com.snews.server.controllers;

import com.snews.server.dto.LoginDto;
import com.snews.server.dto.RegisterDto;
import com.snews.server.dto.UserDto;
import com.snews.server.entities.UserEntity;
import com.snews.server.exceptions.UserAlreadyRegisteredException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path = "/user")
public class UserController {

    //    //    todo remove after adding the real user service
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final UserService userService;

    public UserController(
                          UserService userService) {
        this.userService = userService;
    }

//    @PostMapping(path = "/login")
//    public UserDto login(@RequestBody LoginDto loginDto) {
//        UserDto userdto = new UserDto();
//        userdto.setUsername("haibusa2005")
//                .setRoles(Set.of(
//                        this.userRoleService.getUserRole(UserRoleEnum.USER).getRole().name().toLowerCase(),
//                        this.userRoleService.getUserRole(UserRoleEnum.MODERATOR).getRole().name().toLowerCase()
//                ));
//
//        return userdto;
//    }

    @PostMapping(path = "/register")
    public String register(@RequestBody RegisterDto registerDto) {
//        User user = new User();
//        UserRole role = this.userRoleService.getUserRole(UserRoleEnum.USER);
//        user.setUsername("haibusa2005")
//                .setEmail("haibusa2005@abv.bg")
//                .setUserRole(Set.of(role));
//        return user;
        return "ok";
//        return this.userService.loginUser(loginDto);
//    }
//
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



    @GetMapping(path = "/users-only")
    public String users(){
        return "users only area";
    }

    @GetMapping(path = "/mods-only")
    public String mods(){
        return "moderators only area";
    }

//        return this.userService.loginUser(loginDto);
//    }
//
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



    @GetMapping(path = "/users-only")
    public String users(){
        return "users only area";
    }

    @GetMapping(path = "/mods-only")
    public String mods(){
        return "moderators only area";
    }

    @GetMapping(path = "/admins-only")
    public String admins(){
        return "administrators only area";
    }

}
