package com.snews.server.services.configuration;

import com.snews.server.dto.ServerConfigurationModelDto;
import com.snews.server.dto.UpdateSettingDto;
import com.snews.server.exceptions.MalformedDataException;

public interface ConfigurationService {
    void initConfiguration();

    String getSetting(String setting) throws NoSuchFieldException, IllegalAccessException;

    void modifySetting(UpdateSettingDto dto) throws MalformedDataException;

    void setState(ServerConfigurationModelDto dto);

    ServerConfigurationModelDto getState();

    Boolean isRegistrationEnabled();
}
