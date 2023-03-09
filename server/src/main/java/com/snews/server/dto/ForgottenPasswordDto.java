package com.snews.server.dto;

import jakarta.validation.constraints.Email;

public class ForgottenPasswordDto {
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public ForgottenPasswordDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
