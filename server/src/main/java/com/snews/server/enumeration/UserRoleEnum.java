package com.snews.server.enumeration;

public enum UserRoleEnum {
    USER("user"), MODERATOR("moderator"), ADMINISTRATOR("administrator");

    private String role;

    UserRoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public UserRoleEnum setRole(String role) {
        this.role = role;
        return this;
    }
}
