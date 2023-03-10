package com.snews.server.services.user;

import com.snews.server.dto.ResetPasswordDto;
import com.snews.server.dto.ResetPasswordRequestDto;
import com.snews.server.dto.RegisterDto;
import com.snews.server.dto.UserDto;
import com.snews.server.entities.UserEntity;

public interface UserService{
    UserEntity getUserByEmail(String email);

    UserEntity getUserByUsername(String username);

    UserDto registerUser(RegisterDto dto);

    void initUsers();

    void sendPasswordResetToken(ResetPasswordRequestDto dto);

    boolean validatePasswordResetToken(String token);

    void changePassword(ResetPasswordDto dto);
}
