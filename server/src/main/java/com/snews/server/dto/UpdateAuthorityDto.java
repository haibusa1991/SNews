package com.snews.server.dto;

public class UpdateAuthorityDto {
    private String username;
    private String action;

    public String getUsername() {
        return username;
    }

    public UpdateAuthorityDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAction() {
        return action;
    }

    public UpdateAuthorityDto setAction(String action) {
        this.action = action;
        return this;
    }
}
