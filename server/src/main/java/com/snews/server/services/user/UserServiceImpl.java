package com.snews.server.services.user;

import com.snews.server.dto.LoginDto;
import com.snews.server.dto.RegisterDto;
import com.snews.server.dto.UserDto;
import com.snews.server.entities.UserEntity;
import com.snews.server.entities.UserRoleEntity;
import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.repositories.UserRepository;
import com.snews.server.services.userRole.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return this.userRepository.getUserByEmail(email);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public UserDto registerUser(RegisterDto dto) {
        UserEntity userEntity = new UserEntity();
        UserRoleEntity role = this.userRoleService.getUserRole(UserRoleEnum.USER);
        String candidateUsername = dto.getUsername();
        String candidateEmail = dto.getEmail();

        userEntity.setUsername(candidateUsername)
                .setEmail(candidateEmail)
                .setUserRole(Set.of(role))
                .setPassword(this.passwordEncoder.encode(dto.getPassword()));

        UserEntity registeredUserEntity = this.userRepository.save(userEntity);

        return this.modelMapper.map(registeredUserEntity, UserDto.class);
    }

    @Override
    public UserDto loginUser(LoginDto dto) {
        UserEntity userEntity = this.userRepository.getUserByUsername(dto.getUsername());
        if (userEntity == null) {
            return new UserDto();
        }

        if (!this.passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            return new UserDto();
        }

        return this.modelMapper.map(userEntity, UserDto.class);
    }


    @Override
    public void initUsers() {
        if (this.userRepository.count() > 0) {
            return;
        }

        UserRoleEntity userRole = this.userRoleService.getUserRole(UserRoleEnum.USER);
        UserRoleEntity moderatorRole = this.userRoleService.getUserRole(UserRoleEnum.MODERATOR);
        UserRoleEntity administratorRole = this.userRoleService.getUserRole(UserRoleEnum.ADMINISTRATOR);

        Set<UserEntity> users = new HashSet<>();

        UserEntity user1 = createBaseUser("haibusa2005");
        user1.setUserRole(Set.of(userRole));
        users.add(user1);

        UserEntity user2 = createBaseUser("haibusa2006");
        user2.setUserRole(Set.of(userRole,moderatorRole));
        users.add(user2);

        UserEntity user3 = createBaseUser("haibusa2007");
        user3.setUserRole(Set.of(userRole,administratorRole));
        users.add(user3);

        UserEntity user4 = createBaseUser("haibusa2008");
        user4.setUserRole(Set.of(userRole,moderatorRole,administratorRole));
        users.add(user4);

        this.userRepository.saveAll(users);
    }

    private UserEntity createBaseUser(String username) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(username)
                .setEmail(username + "@abv.bg")
                .setPassword(this.passwordEncoder.encode("123456Aa"));
        return userEntity;
    }
}
