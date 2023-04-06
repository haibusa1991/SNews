package com.snews.server.controllers;

import com.snews.server.dto.*;
import com.snews.server.exceptions.InvalidPasswordResetException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.exceptions.NonExistentUserException;
import com.snews.server.exceptions.UserAlreadyRegisteredException;
import com.snews.server.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.snews.server.utils.Utils.createMalformedDataException;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto,
                                           BindingResult bindingResult) throws UserAlreadyRegisteredException, MalformedDataException {

        if (bindingResult.hasErrors()) {
            throw createMalformedDataException(bindingResult);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/forgotten-password")
    public ResponseEntity<String> forgottenPassword(@RequestBody ResetPasswordRequestDto dto) {
        this.userService.sendPasswordResetToken(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordDto dto, BindingResult bindingResult)
            throws MalformedDataException, InvalidPasswordResetException {

        if (bindingResult.hasErrors()) {
            throw createMalformedDataException(bindingResult);
        }

        boolean isValidToken = this.userService.isValidPasswordResetToken(dto.getRecoveryToken());
        if (!isValidToken) {
            throw new InvalidPasswordResetException("Token timeout or token already used");
        }

        this.userService.changePassword(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser() {
        return  new ResponseEntity<>(this.userService.getCurrentUserAsDto(), HttpStatus.OK);
    }

    @PostMapping(path = "/upload-avatar", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<String> uploadAvatar(MultipartFile image) {
        try {
            this.userService.addAvatar(image);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/remove-avatar")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<String> removeAvatar() {
        this.userService.removeAvatar();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/change-password")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<String> changePassword(NewPasswordDto dto) {
        try {
            this.userService.changePassword(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(path = "/change-email")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<String> changeEmail(NewEmailDto dto) {
        try {
            this.userService.changeEmail(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        UserDto userDto = this.userService.getUserDtoByUsername(username);
        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @PostMapping(path = "/update-authority")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<String> updateAuthority(@RequestBody UpdateAuthorityDto dto) throws NonExistentUserException, MalformedDataException {
        this.userService.updateAuthorities(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
