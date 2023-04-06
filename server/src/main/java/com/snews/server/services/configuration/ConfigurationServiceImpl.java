package com.snews.server.services.configuration;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.snews.server.dto.ServerConfigurationModelDto;
import com.snews.server.dto.UpdateSettingDto;
import com.snews.server.enumeration.ServerConfigurationEnum;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.models.ServerConfigurationModel;
import com.snews.server.services.file.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    private final FileService fileService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public ConfigurationServiceImpl(FileService fileService, ModelMapper modelMapper, Gson gson) {
        this.fileService = fileService;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Object getSetting(String setting) throws NoSuchFieldException, IllegalAccessException {
        ServerConfigurationModel model = readConfiguration();

        Field settingField = model.getClass().getDeclaredField(setting);
        settingField.setAccessible(true);

        return settingField.get(model);
    }

    @Override
    public void modifySetting(UpdateSettingDto dto) throws MalformedDataException {
        ServerConfigurationEnum setting;

        try {
            setting = ServerConfigurationEnum.valueOf(dto.getSetting());
        } catch (Exception e) {
            throw new MalformedDataException("Invalid setting");
        }

        switch (setting) {
            case DISABLE_NEW_USER_REGISTRATION -> disableUserRegistration();
            case ENABLE_NEW_USER_REGISTRATION -> enableUserRegistration();
        }
    }


    @Override
    public void setState(ServerConfigurationModelDto dto) {
        ServerConfigurationModel model = this.modelMapper.map(dto, ServerConfigurationModel.class);
        writeConfiguration(model);
    }

    @Override
    public ServerConfigurationModelDto getState() {
        ServerConfigurationModel model = readConfiguration();
        return this.modelMapper.map(model, ServerConfigurationModelDto.class);
    }

    @Override
    public Boolean isRegistrationEnabled(){
        return this.readConfiguration().isEnableNewUserRegistration();
    }

    private void enableUserRegistration() {
        setProperty("enableNewUserRegistration", true);
    }

    private void disableUserRegistration() {
        setProperty("enableNewUserRegistration", false);
    }

    private void setProperty(String property, boolean value) {
        try {
            ServerConfigurationModel model = readConfiguration();
            Field setting = model.getClass().getDeclaredField(property);
            setting.setAccessible(true);
            setting.setBoolean(model, value);
            writeConfiguration(model);

        } catch (Exception ignored) {
        }
    }

    private ServerConfigurationModel readConfiguration() {

        InputStream config;
        try {
            config = new ByteArrayInputStream(fileService.readConfigurationFile());
        } catch (Exception e) {
            return new ServerConfigurationModel();
        }

        JsonReader json = new JsonReader(new InputStreamReader(config));

        ServerConfigurationModel settings = gson.fromJson(json, ServerConfigurationModel.class);

        if (settings == null) {
            return new ServerConfigurationModel();
        }
        return settings;
    }

    private void writeConfiguration(ServerConfigurationModel configuration) {
        try {
            this.fileService.writeConfigurationFile(gson.toJson(configuration).getBytes());
        } catch (Exception ignored) {
        }
    }


}
