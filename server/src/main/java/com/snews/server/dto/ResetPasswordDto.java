package com.snews.server.dto;

import com.snews.server.annotations.Password;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ResetPasswordDto {

    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Password(message = "Password should contain an uppercase letter, a lowercase letter, and a digit.")
    private String password;

    @NotEmpty(message = "Recovery token cannot be blank.")
    private String recoveryToken;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecoveryToken() {
        return recoveryToken;
    }

    public void setRecoveryToken(String recoveryToken) {
        this.recoveryToken = recoveryToken;
    }
}
