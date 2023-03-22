package com.snews.server.utils;

import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.entities.ArticleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
