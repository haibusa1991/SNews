package com.snews.server.repositories;

import com.snews.server.entities.ResetPasswordTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<ResetPasswordTokenEntity, UUID> {

    ResetPasswordTokenEntity getPasswordResetTokenEntityByToken(String token);

    List<ResetPasswordTokenEntity> getAllByCreatedBeforeOrExhaustedIsTrue(LocalDateTime time15minAgo);
}
