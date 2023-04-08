package com.snews.server.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table( name = "server_settings")
public class ServerSettingEntity {

    @Id
    private String setting;
    private String value;

    public String getSetting() {
        return setting;
    }

    public ServerSettingEntity setSetting(String setting) {
        this.setting = setting;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ServerSettingEntity setValue(String value) {
        this.value = value;
        return this;
    }
}
