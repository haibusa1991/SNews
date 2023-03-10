package com.snews.server.repositories;

import com.snews.server.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity getUserByEmail(String email);

    UserEntity getUserByUsername(String username);

    UserEntity getUserEntityById(UUID id);
}
