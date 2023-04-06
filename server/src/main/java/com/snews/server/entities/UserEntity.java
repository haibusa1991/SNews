package com.snews.server.entities;

import jakarta.persistence.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRoleEntity> userRoles;

    private String avatarId;

    private String defaultAvatarColor;

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public UserEntity setUserRoles(Set<UserRoleEntity> userRoleEntity) {
        this.userRoles = userRoleEntity;
        return this;
    }

    public void addRole(UserRoleEntity userRole) {
        this.userRoles.add(userRole);
    }

    public boolean removeRole(UserRoleEntity userRoleEntity) {
        Optional<UserRoleEntity> roleToRemove = this.userRoles
                .stream()
                .filter(r -> r.getRole() == userRoleEntity.getRole())
                .findFirst();
        return this.userRoles.remove(roleToRemove.orElse(null));
    }

    public String getAvatarId() {
        return avatarId;
    }

    public UserEntity setAvatarId(String avatarId) {
        this.avatarId = avatarId;
        return this;
    }

    public String getDefaultAvatarColor() {
        return defaultAvatarColor;
    }

    public UserEntity setDefaultAvatarColor(String defaultAvatarColor) {
        this.defaultAvatarColor = defaultAvatarColor;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (!id.equals(that.id)) return false;
        if (!email.equals(that.email)) return false;
        return username.equals(that.username);
    }
}
