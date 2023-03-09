package com.snews.server.services.user;

import com.snews.server.dto.ForgottenPasswordDto;
import com.snews.server.dto.RegisterDto;
import com.snews.server.dto.UserDto;
import com.snews.server.entities.UserEntity;

public interface UserService{
    UserEntity getUserByEmail(String email);

    UserEntity getUserByUsername(String username);

    UserDto registerUser(RegisterDto dto);

//    UserDto loginUser(LoginDto dto);

    void initUsers();

    void recoverPassword(ForgottenPasswordDto dto);
}
