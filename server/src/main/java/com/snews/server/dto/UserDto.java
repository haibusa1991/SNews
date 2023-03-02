package com.snews.server.dto;

import com.snews.server.entities.UserRole;

import java.util.Set;

public class UserDto {
    private String username;
    private Set<String> roles;

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


}
