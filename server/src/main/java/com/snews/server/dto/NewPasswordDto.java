package com.snews.server.dto;

public class NewPasswordDto {
    private String newPassword;
    private String currentPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public NewPasswordDto setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public NewPasswordDto setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
        return this;
    }
}
