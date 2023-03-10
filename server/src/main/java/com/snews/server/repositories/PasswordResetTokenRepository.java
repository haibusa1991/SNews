package com.snews.server.repositories;

import com.snews.server.entities.ResetPasswordTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<ResetPasswordTokenEntity, UUID> {

    ResetPasswordTokenEntity getPasswordResetTokenEntityByToken(String token);
}
