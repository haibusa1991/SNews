package com.snews.server.dto;

import jakarta.validation.constraints.Email;

public class ResetPasswordRequestDto {
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public ResetPasswordRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
