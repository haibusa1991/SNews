package com.snews.server.services.user;

import com.snews.server.dto.*;
import com.snews.server.entities.ImageEntity;
import com.snews.server.entities.ResetPasswordTokenEntity;
import com.snews.server.entities.UserEntity;
import com.snews.server.entities.UserRoleEntity;
import com.snews.server.enumeration.AuthorityAction;
import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.exceptions.NonExistentUserException;
import com.snews.server.exceptions.UserAlreadyRegisteredException;
import com.snews.server.repositories.PasswordResetTokenRepository;
import com.snews.server.repositories.UserRepository;
import com.snews.server.services.email.EmailService;
import com.snews.server.services.image.ImageService;
import com.snews.server.services.userRole.UserRoleService;
import org.hibernate.HibernateError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetTokenRepository tokenRepository;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private EmailService emailService;

    @Mock
    private ImageService imageService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    private UserServiceImpl tested;

    UserEntity mockUserEntity = new UserEntity()
            .setUsername("testUser")
            .setEmail("testEmail");

    UserDto mockUserDto = new UserDto()
            .setUsername("testUser")
            .setEmail("testEmail");

    @BeforeEach
    void setUp() {
        this.tested = new UserServiceImpl(
                userRepository,
                tokenRepository,
                userRoleService,
                passwordEncoder,
                modelMapper,
                emailService,
                imageService);

    }

    @Test
    void getUserByEmail() {
        when(userRepository.getUserByEmail("testEmail")).thenReturn(this.mockUserEntity);
        Assertions.assertEquals(tested.getUserByEmail("testEmail").getEmail(), this.mockUserEntity.getEmail());
    }


    @Test
    void getUserByUsername() {
        when(userRepository.getUserByUsername("testUser")).thenReturn(this.mockUserEntity);
        Assertions.assertEquals(tested.getUserByUsername("testUser").getUsername(), this.mockUserEntity.getUsername());
    }

    @Test
    void getUserDtoByUsername() {
        when(userRepository.getUserByUsername("testUser")).thenReturn(this.mockUserEntity);
        when(userRepository.getUserByUsername("non-existent")).thenReturn(null);
        when(modelMapper.map(any(), any())).thenReturn(mockUserDto);

        Assertions.assertEquals(tested.getUserDtoByUsername("testUser").getUsername(), this.mockUserEntity.getUsername());
        Assertions.assertNull(tested.getUserDtoByUsername("non-existent"));
    }

    @Test
    void registerUserThrowsUserAlreadyRegisteredExceptionWhenUsernameIsTaken() {
        when(userRepository.getUserByUsername("testUser")).thenReturn(this.mockUserEntity);

        RegisterDto registerDto = new RegisterDto().setUsername("testUser");
        Assertions.assertThrows(UserAlreadyRegisteredException.class, () -> tested.registerUser(registerDto));
    }

    @Test
    void registerUserThrowsUserAlreadyRegisteredExceptionWhenEmailIsTaken() {
        when(userRepository.getUserByEmail("testEmail")).thenReturn(this.mockUserEntity);

        RegisterDto registerDto = new RegisterDto().setEmail("testEmail");
        Assertions.assertThrows(UserAlreadyRegisteredException.class, () -> tested.registerUser(registerDto));
    }

    @Test
    void registerUserThrowsInternalServerErrorExceptionWhenDatabaseError() {
        when(userRepository.save(any())).thenThrow(new HibernateError("mock error"));
        when(userRoleService.getUserRole(any())).thenReturn(new UserRoleEntity());

        RegisterDto registerDto = new RegisterDto()
                .setEmail("testEmail")
                .setUsername("testUsername")
                .setPassword("testPassword");

        Assertions.assertThrows(InternalServerErrorException.class, () -> tested.registerUser(registerDto));
    }

    @Test
    void registerUserSavesSuccessfully() throws InternalServerErrorException, UserAlreadyRegisteredException {
        when(userRoleService.getUserRole(any())).thenReturn(new UserRoleEntity());

        RegisterDto registerDto = new RegisterDto()
                .setEmail("testEmail")
                .setUsername("testUsername")
                .setPassword("testPassword");

        tested.registerUser(registerDto);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void initUsersWhenUsersExistDoesNotInvokeSaveAll() {
        when(userRepository.count()).thenReturn(10L);
        tested.initUsers();
        verify(userRepository, times(0)).saveAll(any());

    }

    @Test
    void initUsersWithNoUsersInvokesSaveAll() {
        when(userRepository.count()).thenReturn(0L);
        when(userRoleService.getUserRole(UserRoleEnum.USER)).thenReturn(new UserRoleEntity());
        when(userRoleService.getUserRole(UserRoleEnum.MODERATOR)).thenReturn(new UserRoleEntity());
        when(userRoleService.getUserRole(UserRoleEnum.ADMINISTRATOR)).thenReturn(new UserRoleEntity());

        tested.initUsers();
        verify(userRepository, times(1)).saveAll(any());
    }

    @Test
    void sendPasswordResetTokenDoesNothingOnNonExistentEmail() {
        when(userRepository.getUserByEmail(any())).thenReturn(null);
        tested.sendPasswordResetToken(new ResetPasswordRequestDto().setEmail("testEmail"));
        verify(emailService, times(0)).sendMessage(any(), any(), any());

    }

    @Test
    void sendPasswordResetTokenSendsEmail() {
        when(userRepository.getUserByEmail("testEmail")).thenReturn(mockUserEntity);
        tested.sendPasswordResetToken(new ResetPasswordRequestDto().setEmail("testEmail"));
        verify(emailService, times(1)).sendMessage(any(), any(), any());
    }

    @Test
    void isValidPasswordResetTokenReturnsFalseOnExhaustedToken() {
        ResetPasswordTokenEntity token = new ResetPasswordTokenEntity();
        token.setExhausted(true);

        when(tokenRepository.getPasswordResetTokenEntityByToken(any())).thenReturn(token);
        Assertions.assertFalse(tested.isValidPasswordResetToken(any()));
    }

    @Test
    void isValidPasswordResetTokenReturnsFalseOnOverdueToken() {
        ResetPasswordTokenEntity token = new ResetPasswordTokenEntity();
        token.setCreated(LocalDateTime.now().minusSeconds(901));
        token.setExhausted(false);

        when(tokenRepository.getPasswordResetTokenEntityByToken(any())).thenReturn(token);
        when(tokenRepository.save(any())).thenReturn(token);

        Assertions.assertFalse(tested.isValidPasswordResetToken(any()));
    }

    @Test
    void isValidPasswordResetTokenReturnsTrueOnValidToken() {
        ResetPasswordTokenEntity token = new ResetPasswordTokenEntity();
        token.setCreated(LocalDateTime.now());
        token.setExhausted(false);

        when(tokenRepository.getPasswordResetTokenEntityByToken(any())).thenReturn(token);
        when(tokenRepository.save(any())).thenReturn(token);

        Assertions.assertTrue(tested.isValidPasswordResetToken(any()));
    }

    @Test
    void changePasswordAfterResetting() {
        ResetPasswordTokenEntity token = new ResetPasswordTokenEntity();
        token.setCreated(LocalDateTime.now());
        token.setExhausted(false);
        token.setUser(mockUserEntity);

        ResetPasswordDto dto = new ResetPasswordDto();
        dto.setPassword("testPassword");

        when(tokenRepository.getPasswordResetTokenEntityByToken(any())).thenReturn(token);
        when(passwordEncoder.encode(any())).thenReturn("encoded pass");

        tested.changePassword(dto);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void ChangePasswordThrowsAuthenticationExceptionOnInvalidUser() {
        NewPasswordDto dto = new NewPasswordDto();
        dto.setCurrentPassword("old password");
        dto.setNewPassword("new password");

        when(userRepository.getUserByUsername(any())).thenReturn(null);

        Assertions.assertThrows(AuthenticationException.class, () -> tested.changePassword(dto));
    }

    @Test
    void ChangePasswordThrowsAuthenticationExceptionOnInvalidPassword() {
        NewPasswordDto dto = new NewPasswordDto();
        dto.setCurrentPassword("old password");
        dto.setNewPassword("new password");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.getUserByUsername(any())).thenReturn(mockUserEntity);
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        Assertions.assertThrows(AuthenticationException.class, () -> tested.changePassword(dto));
    }

    @Test
    void ChangePasswordSavesNewPassword() throws AuthenticationException {
        NewPasswordDto dto = new NewPasswordDto();
        dto.setCurrentPassword("old password");
        dto.setNewPassword("new password");

        when(userRepository.getUserByUsername(any())).thenReturn(mockUserEntity);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encode(any())).thenReturn("new encoded password");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        tested.changePassword(dto);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void addAvatar() {
        when(userRepository.getUserByUsername(any())).thenReturn(mockUserEntity);

        ImageEntity avatar = new ImageEntity();
        MultipartFile newAvatar = new MockMultipartFile("mock", new byte[0]);
        when(imageService.saveImage(newAvatar)).thenReturn(avatar);


        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        tested.addAvatar(newAvatar);
        verify(userRepository, times(1)).save(any());
    }


    @Test
    void getCurrentUserAsDtoReturnDto() {
        when(userRepository.getUserByUsername(any())).thenReturn(mockUserEntity);
        when(modelMapper.map(any(), any())).thenReturn(mockUserDto);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDto actual = tested.getCurrentUserAsDto();
        UserDto expected = mockUserDto;

        Assertions.assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    void getCurrentUserAsDtoReturnAnonymousUser() {
        when(userRepository.getUserByUsername(any())).thenReturn(new UserEntity().setUsername("anonymousUser"));
        when(modelMapper.map(any(), any())).thenThrow(new IllegalArgumentException("model mapper failed"));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDto expected = new UserDto().setUsername("anonymousUser");
        UserDto actual = tested.getCurrentUserAsDto();

        Assertions.assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    void removeAvatar() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.getUserByUsername(any())).thenReturn(mockUserEntity);
        tested.removeAvatar();
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void getCurrentUser() {
        when(userRepository.getUserByUsername(any())).thenReturn(mockUserEntity);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Assertions.assertEquals(mockUserEntity, tested.getCurrentUser());
    }

    @Test
    void changeEmailThrowsAuthenticationExceptionOnAnonymousUser() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.getUserByUsername(any())).thenReturn(null);

        NewEmailDto dto = new NewEmailDto().setNewEmail("newEmail").setCurrentPassword("password");

        Assertions.assertThrows(AuthenticationException.class, () -> tested.changeEmail(dto));

    }

    @Test
    void changeEmailThrowsAuthenticationExceptionOnInvalidPassword() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.getUserByUsername(any())).thenReturn(mockUserEntity);
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        NewEmailDto dto = new NewEmailDto().setNewEmail("newEmail").setCurrentPassword("password");

        Assertions.assertThrows(AuthenticationException.class, () -> tested.changeEmail(dto));

    }

    @Test
    void changeEmailSetsNewEmail() throws AuthenticationException {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.getUserByUsername(any())).thenReturn(mockUserEntity);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        NewEmailDto dto = new NewEmailDto().setNewEmail("newEmail").setCurrentPassword("password");
        tested.changeEmail(dto);
        verify(userRepository, times(1)).save(any());

    }

    @Test
    void updateAuthoritiesThrowsNonExistentUserException() {
        when(userRepository.getUserByUsername(any())).thenReturn(null);
        Assertions.assertThrows(NonExistentUserException.class, () -> tested.updateAuthorities(new UpdateAuthorityDto()));
    }

    @Test
    void updateAuthoritiesThrowsMalformedDataException() {
        when(userRepository.getUserByUsername(any())).thenReturn(mockUserEntity);
        Assertions.assertThrows(MalformedDataException.class, () -> tested.updateAuthorities(new UpdateAuthorityDto().setAction("invalid action")));

    }

    @Test
    void updateAuthoritiesThrowsMalformedDataExceptionOnSelfDemote() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.getUserByUsername(any())).thenReturn(mockUserEntity);

        UpdateAuthorityDto dto = new UpdateAuthorityDto().setAction("REMOVE_ADMINISTRATOR").setUsername(mockUserEntity.getUsername());

        Assertions.assertThrows(MalformedDataException.class, () -> tested.updateAuthorities(dto));
    }

    @Test
    void updateAuthoritiesUpdatesAuthorities() throws MalformedDataException, NonExistentUserException {
        UserEntity mockUserEntity2 = new UserEntity()
                .setUsername("testUser2")
                .setEmail("testEmail2")
                .setUserRoles(new HashSet<>());

        List<UpdateAuthorityDto> authDtos = Arrays.stream(AuthorityAction.values())
                .map(e -> new UpdateAuthorityDto()
                        .setUsername(mockUserEntity2.getUsername())
                        .setAction(e.name()))
                .toList();

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("testUser");
        when(userRepository.getUserByUsername("testUser2")).thenReturn(mockUserEntity2);
        when(userRepository.getUserByUsername("testUser")).thenReturn(mockUserEntity);

        when(userRoleService.getUserRole(UserRoleEnum.MODERATOR)).thenReturn(new UserRoleEntity().setRole(UserRoleEnum.MODERATOR));
        when(userRoleService.getUserRole(UserRoleEnum.ADMINISTRATOR)).thenReturn(new UserRoleEntity().setRole(UserRoleEnum.ADMINISTRATOR));

        for (UpdateAuthorityDto authDto : authDtos) {
            tested.updateAuthorities(authDto);
        }
        verify(userRepository, times(4)).save(any());
    }


    @Test
    void removeInvalidPasswordRecoveryTokens() {
        List<ResetPasswordTokenEntity> invalidTokens = List.of(new ResetPasswordTokenEntity());
        when(tokenRepository.getAllByCreatedBeforeOrExhaustedIsTrue(any())).thenReturn(invalidTokens);
        tested.removeInvalidPasswordRecoveryTokens();
        verify(tokenRepository, times(1)).deleteAll(invalidTokens);
    }
}