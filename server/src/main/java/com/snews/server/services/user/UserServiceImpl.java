package com.snews.server.services.user;

import com.snews.server.dto.*;
import com.snews.server.entities.ResetPasswordTokenEntity;
import com.snews.server.entities.UserEntity;
import com.snews.server.entities.UserRoleEntity;
import com.snews.server.enumeration.AuthorityAction;
import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.exceptions.NonExistentUserException;
import com.snews.server.repositories.PasswordResetTokenRepository;
import com.snews.server.repositories.UserRepository;
import com.snews.server.services.email.EmailService;
import com.snews.server.services.file.FileService;
import com.snews.server.services.userRole.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;

    private final UserRoleService userRoleService;
    private final FileService fileService;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordResetTokenRepository tokenRepository,
                           UserRoleService userRoleService,
                           FileService fileService,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.userRoleService = userRoleService;
        this.fileService = fileService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
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
    public UserDto getUserDtoByUsername(String username) {
        UserEntity user = this.userRepository.getUserByUsername(username);
        if (user != null) {
            return this.modelMapper.map(user, UserDto.class);
        }
        return null;
    }

    @Override
    public UserDto registerUser(RegisterDto dto) {
        UserEntity userEntity = new UserEntity();
        UserRoleEntity role = this.userRoleService.getUserRole(UserRoleEnum.USER);
        String candidateUsername = dto.getUsername();
        String candidateEmail = dto.getEmail();

        userEntity.setUsername(candidateUsername)
                .setEmail(candidateEmail)
                .setUserRoles(Set.of(role))
                .setPassword(this.passwordEncoder.encode(dto.getPassword()))
                .setDefaultAvatarColor(getRandomAvatarColor());

        UserEntity registeredUserEntity = this.userRepository.save(userEntity);

        return this.modelMapper.map(registeredUserEntity, UserDto.class);
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
        user1.setUserRoles(Set.of(userRole));
        users.add(user1);

        UserEntity user2 = createBaseUser("haibusa2006");
        user2.setUserRoles(Set.of(userRole, moderatorRole));
        users.add(user2);

        UserEntity user3 = createBaseUser("haibusa2007");
        user3.setUserRoles(Set.of(userRole, administratorRole));
        users.add(user3);

        UserEntity user4 = createBaseUser("haibusa2008");
        user4.setUserRoles(Set.of(userRole, moderatorRole, administratorRole));
        users.add(user4);

        this.userRepository.saveAll(users);
    }

    private UserEntity createBaseUser(String username) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(username)
                .setEmail(username + "@abv.bg")
                .setPassword(this.passwordEncoder.encode("123456Aa"))
                .setDefaultAvatarColor(getRandomAvatarColor());
        return userEntity;
    }

    @Override
    public void sendPasswordResetToken(ResetPasswordRequestDto dto) {
        UserEntity user = this.userRepository.getUserByEmail(dto.getEmail());

        if (user == null) {
            return;
        }

        String passwordResetToken = getPasswordResetToken(user);
        this.emailService.sendMessage(user.getUsername(), user.getEmail(), "http://localhost:8080/user/password-reset/" + passwordResetToken);
    }

    private String getPasswordResetToken(UserEntity user) {
        ResetPasswordTokenEntity passwordResetToken = new ResetPasswordTokenEntity();

        Random r = new SecureRandom();
        byte[] bytes = new byte[33];
        r.nextBytes(bytes);

        String token = Base64.getUrlEncoder().encodeToString(bytes);

        passwordResetToken.setToken(token)
                .setCreated(LocalDateTime.now())
                .setUser(user);

        this.tokenRepository.save(passwordResetToken);

        return token;
    }

    @Override
    public boolean validatePasswordResetToken(String token) {
        ResetPasswordTokenEntity tokenEntity = this.tokenRepository.getPasswordResetTokenEntityByToken(token);

        if (tokenEntity.isExhausted()) {
            return false;
        }

        tokenEntity.setExhausted(true);
        ResetPasswordTokenEntity exhaustedToken = this.tokenRepository.save(tokenEntity);

        long elapsedTimeSeconds = Duration.between(LocalDateTime.now(), exhaustedToken.getCreated()).abs().toSeconds();

        if (elapsedTimeSeconds > 900) {
            return false;
        }

        return true;
    }

    @Override
    public void changePassword(ResetPasswordDto dto) {
        ResetPasswordTokenEntity tokenEntity = this.tokenRepository.getPasswordResetTokenEntityByToken(dto.getRecoveryToken());
        UserEntity user = tokenEntity.getUser();

        user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        this.userRepository.save(user);
    }

    @Override
    public void changePassword(NewPasswordDto dto) throws AuthenticationException {
        UserEntity currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new AuthenticationException("Not a user");
        }


        if (!this.passwordEncoder.matches(dto.getCurrentPassword(), currentUser.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        currentUser.setPassword(this.passwordEncoder.encode(dto.getNewPassword()));
        this.userRepository.save(currentUser);
    }

    @Override
    public void addAvatar(MultipartFile image) throws IOException {
        String avatarId = fileService.saveAvatarToDisk(image.getBytes());
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        UserEntity user = this.userRepository.getUserByUsername(username);

        user.setAvatarId(avatarId);
        this.userRepository.save(user);
    }

    @Override
    public UserDto getUserDto() {
        UserEntity currentUser = getCurrentUser();

        try {
            return this.modelMapper.map(currentUser, UserDto.class);
        } catch (Exception e) {
            return new UserDto().setUsername("anonymousUser");
        }
    }

    @Override
    public void removeAvatar() {
        UserEntity currentUser = getCurrentUser();
        currentUser.setAvatarId(null);
        this.userRepository.save(currentUser);
    }

    private String getRandomAvatarColor() {
        Random r = new Random();

        StringBuilder color = new StringBuilder(7);
        color.append("#");
        for (int i = 0; i < 3; i++) {
            color.append(Integer.toHexString(r.nextInt(96) + 64));
        }
        return color.toString();
    }

    private UserEntity getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userRepository.getUserByUsername(currentUsername);
    }

    @Override
    public void changeEmail(NewEmailDto dto) throws AuthenticationException {
        UserEntity currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new AuthenticationException("Not a user");
        }


        if (!this.passwordEncoder.matches(dto.getCurrentPassword(), currentUser.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        currentUser.setEmail(dto.getNewEmail());
        this.userRepository.save(currentUser);
    }

    @Override
    public void updateAuthorities(UpdateAuthorityDto dto) throws NonExistentUserException, MalformedDataException {
        UserEntity targetUser = this.userRepository.getUserByUsername(dto.getUsername());
        if (targetUser == null) {
            throw new NonExistentUserException("No user with username " + dto.getUsername() + " exists.");
        }

        AuthorityAction action;
        try {
            action = AuthorityAction.valueOf(dto.getAction());
        } catch (Exception e) {
            throw new MalformedDataException("Invalid action.");
        }


        switch (action) {
            case ADD_MODERATOR -> targetUser.addRole(userRoleService.getUserRole(UserRoleEnum.MODERATOR));
            case REMOVE_MODERATOR -> targetUser.removeRole(userRoleService.getUserRole(UserRoleEnum.MODERATOR));
            case ADD_ADMINISTRATOR -> targetUser.addRole(userRoleService.getUserRole(UserRoleEnum.ADMINISTRATOR));
            case REMOVE_ADMINISTRATOR -> {
                UserEntity currentUser = getCurrentUser();
                if(currentUser.equals(targetUser)){
                    throw new MalformedDataException("You cannot demote yourself, dummy.");
                }
                targetUser.removeRole(userRoleService.getUserRole(UserRoleEnum.ADMINISTRATOR));
            }
        }

        this.userRepository.save(targetUser);
    }
}
