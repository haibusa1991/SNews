package com.snews.server.configuration;

import com.snews.server.dto.UserDto;
import com.snews.server.entities.UserEntity;
import com.snews.server.entities.UserRoleEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.context.annotation.Configuration
public class BeansConfiguration {


    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Set<UserRoleEntity>, Set<String>> userRoleConverter = context ->
                context.getSource()
                        .stream()
                        .map(UserRoleEntity::getRole)
                        .map(Enum::name)
                        .collect(Collectors.toSet());

        modelMapper.createTypeMap(UserEntity.class, UserDto.class)
                .addMappings(m -> m.using(userRoleConverter).map(UserEntity::getUserRoles, UserDto::setRoles));

        return modelMapper;
    }


}
