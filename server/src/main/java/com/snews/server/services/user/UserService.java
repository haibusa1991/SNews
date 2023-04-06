package com.snews.server.services.user;

import com.snews.server.dto.*;
import com.snews.server.entities.UserEntity;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.exceptions.NonExistentUserException;
import com.snews.server.exceptions.UserAlreadyRegisteredException;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.IOException;

public interface UserService{
    UserEntity getUserByEmail(String email);

    UserEntity getUserByUsername(String username);

    UserDto getUserDtoByUsername(String username);

    void registerUser(RegisterDto dto) throws UserAlreadyRegisteredException, InternalServerErrorException;

    void initUsers();

    void sendPasswordResetToken(ResetPasswordRequestDto dto);

    boolean validatePasswordResetToken(String token);

    void changePassword(ResetPasswordDto dto);

    void changePassword(NewPasswordDto dto) throws AuthenticationException;

    void addAvatar(MultipartFile image) throws IOException;

    UserDto getUserDto();

    void removeAvatar();

    void changeEmail(NewEmailDto dto) throws AuthenticationException;

    void updateAuthorities(UpdateAuthorityDto dto) throws NonExistentUserException, MalformedDataException;

    void removeInvalidPasswordRecoveryTokens();
}
