package com.snews.server.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reset_password_tokens")
public class ResetPasswordTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime created;

    private String token;

    public boolean isExhausted() {
        return exhausted;
    }

    public void setExhausted(boolean exhausted) {
        this.exhausted = exhausted;
    }

    private boolean exhausted = false;

    public ResetPasswordTokenEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ResetPasswordTokenEntity setToken(String token) {
        this.token = token;
        return this;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    public UUID getId() {
        return id;
    }


    public LocalDateTime getCreated() {
        return created;
    }

    public ResetPasswordTokenEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public ResetPasswordTokenEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}


