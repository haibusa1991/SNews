package com.snews.server.dto;

import java.util.Set;

public class UserDto {
    private String username;
    private Set<String> roles;
    private String avatarId;
    private String defaultAvatarColor;
    private String email;

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public UserDto setRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public UserDto setAvatarId(String avatarId) {
        this.avatarId = avatarId;
        return this;
    }

    public String getDefaultAvatarColor() {
        return defaultAvatarColor;
    }

    public UserDto setDefaultAvatarColor(String defaultAvatarColor) {
        this.defaultAvatarColor = defaultAvatarColor;
        return this;
    }
}
