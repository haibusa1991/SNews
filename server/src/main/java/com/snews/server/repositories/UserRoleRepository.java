package com.snews.server.repositories;

import com.snews.server.entities.UserRole;
import com.snews.server.enumeration.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    UserRole getUserRoleByRole(UserRoleEnum userRoleEnum);
}
