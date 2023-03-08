package com.snews.server.dto;

import com.snews.server.annotations.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterDto {
    @NotEmpty (message = "Username cannot be empty.")
    @Size(min = 3, message = "Username must be at least 3 characters long.")
    private String username;

    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Password(message = "Password should contain an uppercase letter, a lowercase letter, and a digit.")
    private String password;

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Must be a well-formed email address.")
    private String email;

    public String getUsername() {
        return username;
    }

    public RegisterDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
