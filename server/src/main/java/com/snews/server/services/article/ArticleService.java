package com.snews.server.services.article;

import com.snews.server.dto.ArticleDto;
import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.NewArticleDto;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.exceptions.MalformedDataException;

import java.io.IOException;

public interface ArticleService {


    String save(NewArticleDto dto) throws IOException, InternalServerErrorException, MalformedDataException;

    String[] getArticleCategories();

    ArticleDto getArticle(String articleUrl);

    ArticleOverviewDto[] getRecentArticles(int articleCount);

    ArticleOverviewDto[] getTodayArticles();

}
