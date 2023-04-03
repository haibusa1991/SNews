package com.snews.server.controllers;

import com.snews.server.dto.*;
import com.snews.server.entities.UserEntity;
import com.snews.server.exceptions.InvalidPasswordResetException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.exceptions.UserAlreadyRegisteredException;
import com.snews.server.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public UserDto register(@RequestBody @Valid RegisterDto registerDto,
                            BindingResult bindingResult) throws UserAlreadyRegisteredException, MalformedDataException {

        if (bindingResult.hasErrors()) {
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
        return this.userService.getUserDto();
//        return userDto;

    }

    private MalformedDataException getMalformedDataException(BindingResult result) {
        String errorMessages = result
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(System.lineSeparator()));

        return new MalformedDataException(errorMessages);
    }

    @PostMapping(path = "/upload-avatar", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> uploadAvatar(MultipartFile image) {
        try {
            this.userService.addAvatar(image);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/remove-avatar")
    public ResponseEntity<String> removeAvatar() {
        this.userService.removeAvatar();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/change-password")
    public ResponseEntity<String> changePassword(NewPasswordDto dto) {
        try {
            this.userService.changePassword(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(path = "/change-email")
    public ResponseEntity<String> changeEmail(NewEmailDto dto) {
        try {
            this.userService .changeEmail(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
