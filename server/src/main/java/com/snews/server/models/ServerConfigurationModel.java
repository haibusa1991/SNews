package com.snews.server.models;

public class ServerConfigurationModel {
    private boolean enableNewUserRegistration = true;

    public boolean isEnableNewUserRegistration() {
        return enableNewUserRegistration;
    }

    public ServerConfigurationModel setEnableNewUserRegistration(boolean enableNewUserRegistration) {
        this.enableNewUserRegistration = enableNewUserRegistration;
        return this;
    }
}
