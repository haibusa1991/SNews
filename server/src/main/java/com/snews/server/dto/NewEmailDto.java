package com.snews.server.dto;

public class NewEmailDto {
    private String newEmail;
    private String currentPassword;

    public String getNewEmail() {
        return newEmail;
    }

    public NewEmailDto setNewEmail(String newEmail) {
        this.newEmail = newEmail;
        return this;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public NewEmailDto setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
        return this;
    }
}
