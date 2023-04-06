package com.snews.server.utils;

import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.entities.ArticleEntity;
import com.snews.server.exceptions.MalformedDataException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Utils implements ApplicationContextAware {
    private static ApplicationContext context;

    public static ArticleOverviewDto[] convertToDtoArray(List<ArticleEntity> articleEntityList) {
        ModelMapper mapper = (ModelMapper) context.getBean("ModelMapper");
        return articleEntityList.stream().map(article -> mapper.map(article, ArticleOverviewDto.class)).toArray(ArticleOverviewDto[]::new);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static MalformedDataException createMalformedDataException(BindingResult result) {
        String errorMessages = result
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(System.lineSeparator()));

        return new MalformedDataException(errorMessages);
    }
}
