package com.snews.server.repositories;

import com.snews.server.entities.ServerSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerSettingsRepository extends JpaRepository<ServerSettingEntity, String> {

    ServerSettingEntity getServerSettingEntityBySetting(String setting);
}
