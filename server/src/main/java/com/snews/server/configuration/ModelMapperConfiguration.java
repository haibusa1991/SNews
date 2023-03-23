package com.snews.server.configuration;

import com.snews.server.dto.ArticleDto;
import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.UserDto;
import com.snews.server.entities.ArticleEntity;
import com.snews.server.entities.ArticleTagEntity;
import com.snews.server.entities.UserEntity;
import com.snews.server.entities.UserRoleEntity;
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


        Converter<Set<ArticleTagEntity>, String[]> articleTagsConverter = context ->
                context.getSource()
                        .stream()
                        .map(e -> e.getTag().name())
                        .toArray(String[]::new);

        Converter<String, String[]> articleContentConverter = context ->
                context.getSource().split("\n");

        modelMapper.createTypeMap(ArticleEntity.class, ArticleDto.class)
                .addMappings(m -> m.using(articleTagsConverter).map(ArticleEntity::getTags, ArticleDto::setArticleTags))
                .addMappings(m -> m.using(articleContentConverter).map(ArticleEntity::getContent, ArticleDto::setContent));


        Converter<Set<UserRoleEntity>, Set<String>> userRoleConverter = context ->
                context.getSource()
                        .stream()
                        .map(UserRoleEntity::getRole)
                        .map(Enum::name)
                        .collect(Collectors.toSet());

        modelMapper.createTypeMap(UserEntity.class, UserDto.class)
                .addMappings(m -> m.using(userRoleConverter).map(UserEntity::getUserRoles, UserDto::setRoles));


        Converter<String, String> articleOverviewDtoSetThumbnail = context ->
//                "assets/storage/articles/thumbnails/" + context.getSource() + "_thumb";
                context.getSource();

        Converter<String, String> articleOverviewDtoSetHref = context ->
                "/news/article/" + context.getSource();

        modelMapper.createTypeMap(ArticleEntity.class, ArticleOverviewDto.class)
                .addMappings(m -> m.using(articleOverviewDtoSetHref).map(ArticleEntity::getHref, ArticleOverviewDto::setHref))
                .addMappings(m -> m.using(articleOverviewDtoSetThumbnail).map(ArticleEntity::getPicture, ArticleOverviewDto::setThumbnailUrl))
                .addMapping(ArticleEntity::getPublished, ArticleOverviewDto::setPublished);


        return modelMapper;
    }
}
