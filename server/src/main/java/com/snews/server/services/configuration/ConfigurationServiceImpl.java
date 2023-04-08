package com.snews.server.services.configuration;

import com.snews.server.dto.ServerConfigurationModelDto;
import com.snews.server.dto.UpdateSettingDto;
import com.snews.server.entities.ServerSettingEntity;
import com.snews.server.enumeration.ServerConfigurationEnum;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.models.ServerConfigurationModel;
import com.snews.server.repositories.ServerSettingsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    private final ModelMapper modelMapper;
    private final ServerSettingsRepository serverSettingsRepository;

    public ConfigurationServiceImpl(ModelMapper modelMapper, ServerSettingsRepository serverSettingsRepository) {
        this.modelMapper = modelMapper;
        this.serverSettingsRepository = serverSettingsRepository;
    }

    @Override
    public void initConfiguration() {
        if (this.serverSettingsRepository.count() > 0) {
            return;
        }

        ServerSettingEntity enableNewUserRegistration = new ServerSettingEntity()
                .setSetting("enableNewUserRegistration")
                .setValue("true");
        this.serverSettingsRepository.save(enableNewUserRegistration);
    }

    @Override
    public String getSetting(String setting) {
        return this.serverSettingsRepository.getServerSettingEntityBySetting(setting).getValue();
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

        ServerSettingEntity enableNewUserRegistration = this.serverSettingsRepository.getServerSettingEntityBySetting("enableNewUserRegistration");
        enableNewUserRegistration.setValue(model.isEnableNewUserRegistration() ? "true" : "false");
        this.serverSettingsRepository.save(enableNewUserRegistration);
    }

    @Override
    public ServerConfigurationModelDto getState() {
        ServerConfigurationModel model = new ServerConfigurationModel();
        ServerSettingEntity setting = this.getSettingEntity("enableNewUserRegistration");
        model.setEnableNewUserRegistration(setting.getValue().equals("true"));
        return this.modelMapper.map(model, ServerConfigurationModelDto.class);
    }

    @Override
    public Boolean isRegistrationEnabled() {
        return this.getSettingEntity("enableNewUserRegistration").getValue().equals("true");
    }

    private void enableUserRegistration() {
        ServerSettingEntity setting = this.getSettingEntity("enableNewUserRegistration");
        setting.setValue("true");
        this.serverSettingsRepository.save(setting);
    }

    private void disableUserRegistration() {
        ServerSettingEntity setting = this.getSettingEntity("enableNewUserRegistration");
        setting.setValue("false");
        this.serverSettingsRepository.save(setting);
    }


    private ServerSettingEntity getSettingEntity(String setting) {
        return this.serverSettingsRepository.getServerSettingEntityBySetting(setting);
    }
}
