package com.snews.server.dto;

import jakarta.validation.constraints.NotNull;

public class ServerConfigurationModelDto {

    @NotNull
    private boolean enableNewUserRegistration;

    public boolean isEnableNewUserRegistration() {
        return enableNewUserRegistration;
    }

    public void setEnableNewUserRegistration(boolean enableNewUserRegistration) {
        this.enableNewUserRegistration = enableNewUserRegistration;
    }
}
