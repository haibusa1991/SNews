package com.snews.server.configuration;

import com.snews.server.dto.ArticleDto;
import com.snews.server.dto.UserDto;
import com.snews.server.entities.ArticleEntity;
import com.snews.server.entities.ArticleTagEntity;
import com.snews.server.entities.UserEntity;
import com.snews.server.entities.UserRoleEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.context.annotation.Configuration
public class BeansConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Set<UserRoleEntity>, Set<String>> userRoleConverter = context ->
                context.getSource()
                        .stream()
                        .map(UserRoleEntity::getRole)
                        .map(Enum::name)
                        .collect(Collectors.toSet());

        Converter<Set<ArticleTagEntity>, String[]> articleTagsConverter = context ->
                context.getSource()
                        .stream()
                        .map(e -> e.getTag().name())
                        .toArray(String[]::new);

        Converter<String, String[]> articleContentConverter = context ->
                context.getSource().split("\n");

        modelMapper.createTypeMap(UserEntity.class, UserDto.class)
                .addMappings(m -> m.using(userRoleConverter).map(UserEntity::getUserRoles, UserDto::setRoles));

        modelMapper.createTypeMap(ArticleEntity.class, ArticleDto.class)
                .addMappings(m -> m.using(articleTagsConverter).map(ArticleEntity::getTags, ArticleDto::setArticleTags))
                .addMappings(m -> m.using(articleContentConverter).map(ArticleEntity::getContent, ArticleDto::setContent));

        return modelMapper;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setDefaultEncoding("UTF-8");

        mailSender.setUsername(System.getenv("GmailUsername"));
        mailSender.setPassword(System.getenv("GmailPasswordToken"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        //todo remove
        props.put("mail.debug", "true");

        return mailSender;
    }

}
