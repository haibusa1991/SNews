package com.snews.server.configuration;

import com.snews.server.dto.ArticleDto;
import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.UserDto;
import com.snews.server.entities.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfiguration {

    @Bean(name = "ModelMapper")
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        Converter<Set<ArticleCategoryEntity>, String[]> articleCategoriesConverter = context ->
                context.getSource()
                        .stream()
                        .map(e -> e.getCategory().name())
                        .toArray(String[]::new);

        Converter<String, String[]> articleContentConverter = context ->
                context.getSource().split("\n");

        Converter<ImageEntity, String> imageEntityConverter = context ->
                context.getSource() == null? "": context.getSource().getId().toString();

        modelMapper.createTypeMap(ArticleEntity.class, ArticleDto.class)
                .addMappings(m -> m.using(articleCategoriesConverter).map(ArticleEntity::getCategories, ArticleDto::setCategories))
                .addMappings(m -> m.using(articleContentConverter).map(ArticleEntity::getContent, ArticleDto::setContent))
                .addMappings(m->m.using(imageEntityConverter).map(ArticleEntity::getImage,ArticleDto::setImage))
                .addMappings(m->m.using(imageEntityConverter).map(ArticleEntity::getThumbnail,ArticleDto::setThumbnail));


        Converter<Set<UserRoleEntity>, Set<String>> userRoleConverter = context ->
                context.getSource()
                        .stream()
                        .map(UserRoleEntity::getRole)
                        .map(Enum::name)
                        .collect(Collectors.toSet());

        modelMapper.createTypeMap(UserEntity.class, UserDto.class)
                .addMappings(m -> m.using(userRoleConverter).map(UserEntity::getUserRoles, UserDto::setRoles))
                .addMappings(m -> m.using(imageEntityConverter).map(UserEntity::getAvatar, UserDto::setAvatar));


        Converter<String, String> articleOverviewDtoSetHref = context ->
                "/article/" + context.getSource();

        modelMapper.createTypeMap(ArticleEntity.class, ArticleOverviewDto.class)
                .addMappings(m -> m.using(articleOverviewDtoSetHref).map(ArticleEntity::getHref, ArticleOverviewDto::setHref))
                .addMappings(m -> m.using(imageEntityConverter).map(ArticleEntity::getThumbnail, ArticleOverviewDto::setThumbnail))
                .addMapping(ArticleEntity::getPublished, ArticleOverviewDto::setPublished);


        return modelMapper;
    }
}
