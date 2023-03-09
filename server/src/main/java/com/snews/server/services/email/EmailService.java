package com.snews.server.services.email;

public interface EmailService {
    void sendMessage(String username, String passwordResetUrl);
}
