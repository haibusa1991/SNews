package com.snews.server.controllers;

import com.snews.server.dto.ResetPasswordRequestDto;
import com.snews.server.dto.RegisterDto;
import com.snews.server.dto.ResetPasswordDto;
import com.snews.server.dto.UserDto;
import com.snews.server.entities.UserEntity;
import com.snews.server.exceptions.InvalidPasswordResetException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.exceptions.UserAlreadyRegisteredException;
import com.snews.server.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    //    private final UserRoleService userRoleService;
    private final UserService userService;

    public UserController(
//            UserRoleService userRoleService,
            UserService userService) {
//        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public UserDto register(@RequestBody @Valid RegisterDto registerDto,
                            BindingResult bindingResult) throws UserAlreadyRegisteredException, MalformedDataException {

        if (bindingResult.hasErrors()) {
//            String errorMessages = bindingResult
//                    .getAllErrors()
//                    .stream()
//                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                    .collect(Collectors.joining(System.lineSeparator()));
//            throw new MalformedDataException(errorMessages);
            throw getMalformedDataException(bindingResult);
        }

//todo refactor -> move logic to service. Service returns enum - OK, usernameError, emailError
        String candidateUsername = registerDto.getUsername();
        UserEntity userEntityByUsername = this.userService.getUserByUsername(candidateUsername);
        if (userEntityByUsername != null && candidateUsername.equalsIgnoreCase(userEntityByUsername.getUsername())) {
            throw new UserAlreadyRegisteredException("Username already registered.");
        }

        String candidateEmail = registerDto.getEmail();
        UserEntity userEntityByEmail = this.userService.getUserByEmail(candidateEmail);
        if (userEntityByEmail != null && candidateEmail.equalsIgnoreCase(userEntityByEmail.getEmail())) {
            throw new UserAlreadyRegisteredException("Email address is already registered.");
        }

        return this.userService.registerUser(registerDto);

    }

    @PostMapping(path = "/forgotten-password")
    public void forgottenPassword(@RequestBody ResetPasswordRequestDto dto) {
        this.userService.sendPasswordResetToken(dto);
//        System.out.println("--------------------------********************************-------------------------------");
    }

    @PostMapping(path = "/reset-password")
    public void resetPassword(@RequestBody @Valid ResetPasswordDto dto, BindingResult bindingResult)
            throws MalformedDataException, InvalidPasswordResetException {

        if (bindingResult.hasErrors()) {
            throw getMalformedDataException(bindingResult);
        }

        if (!this.userService.validatePasswordResetToken(dto.getRecoveryToken())) {
            throw new InvalidPasswordResetException("Token timeout or token already used");
        }

        this.userService.changePassword(dto);
    }

    @GetMapping("/username")
    public String getUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @GetMapping("/user")
    public UserDto getUser() {
        UserDto userDto = new UserDto();

        userDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        Set<String> roles = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(e -> e.replaceAll("ROLE_", ""))
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        userDto.setRoles(roles);

        return userDto;

    }

    private MalformedDataException getMalformedDataException(BindingResult result) {
        String errorMessages = result
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(System.lineSeparator()));

        return new MalformedDataException(errorMessages);
    }

}
