package com.snews.server.services.user;

import com.snews.server.dto.*;
import com.snews.server.entities.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService{
    UserEntity getUserByEmail(String email);

    UserEntity getUserByUsername(String username);

    UserDto registerUser(RegisterDto dto);

    void initUsers();

    void sendPasswordResetToken(ResetPasswordRequestDto dto);

    boolean validatePasswordResetToken(String token);

    void changePassword(ResetPasswordDto dto);

    void addAvatar(MultipartFile image) throws IOException;

    UserDto getUserDto();

    void removeAvatar();
}
