package com.snews.server.services.configuration;

import com.snews.server.dto.ServerConfigurationModelDto;
import com.snews.server.dto.UpdateSettingDto;
import com.snews.server.entities.ServerSettingEntity;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.models.ServerConfigurationModel;
import com.snews.server.repositories.ServerSettingsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfigurationServiceImplTest {
    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private ServerSettingsRepository mockServerSettingsRepository;

    private ServerSettingEntity mockServerSettingEntity = new ServerSettingEntity()
            .setSetting("test setting")
            .setValue("test value");

    ConfigurationServiceImpl tested;

    @BeforeEach
    void setUp() {
        this.tested = new ConfigurationServiceImpl(mockModelMapper, mockServerSettingsRepository);
    }

    @Test
    void initConfigurationReturnsIfRepoContainsEntities() {
        when(mockServerSettingsRepository.count()).thenReturn(10L);
        tested.initConfiguration();
        verify(mockServerSettingsRepository, times(0)).save(any());
    }

    @Test
    void initConfigurationSavesSetting() {
        when(mockServerSettingsRepository.count()).thenReturn(0L);
        tested.initConfiguration();
        verify(mockServerSettingsRepository, times(1)).save(any());
    }

    @Test
    void getSettingReturnsCorrect() {
        when(mockServerSettingsRepository.getServerSettingEntityBySetting(any())).thenReturn(mockServerSettingEntity);
        String expected = this.mockServerSettingEntity.getValue();
        String actual = tested.getSetting(mockServerSettingEntity.getSetting());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void modifySettingThrowsMalformedDataExceptionOnInvalidInput() {
        Assertions.assertThrows(MalformedDataException.class, () -> tested.modifySetting(new UpdateSettingDto().setSetting("INVALID")));
    }

    @Test
    void setState() {
        when(mockModelMapper.map(any(),any())).thenReturn(new ServerConfigurationModel());
        when(mockServerSettingsRepository.getServerSettingEntityBySetting(any())).thenReturn(new ServerSettingEntity());

        ServerConfigurationModelDto dto = new ServerConfigurationModelDto();
        dto.setEnableNewUserRegistration(true);
        tested.setState(dto);
        verify(mockServerSettingsRepository, times(1)).save(any());
    }

}