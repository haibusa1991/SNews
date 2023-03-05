package com.snews.server.entities;

import com.snews.server.enumeration.UserRoleEnum;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_roles")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public UserRoleEntity(UserRoleEnum role) {
        this.role = role;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }

    public UserRoleEntity() {
    }
}
