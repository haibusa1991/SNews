package com.snews.server.services.configuration;

public class ServerConfiguration {
    private boolean registrationEnabled;

    public boolean isRegistrationEnabled() {
        return registrationEnabled;
    }

    public ServerConfiguration setRegistrationEnabled(boolean registrationEnabled) {
        this.registrationEnabled = registrationEnabled;
        return this;
    }
}
