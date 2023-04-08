package com.snews.server.services.article;

import com.snews.server.dto.ArticleDto;
import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.NewArticleDto;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.exceptions.MalformedDataException;

public interface ArticleService {


    String addArticle(NewArticleDto dto) throws InternalServerErrorException, MalformedDataException;

    String[] getArticleCategories();

    ArticleDto getArticle(String articleUrl) throws MalformedDataException;

    ArticleOverviewDto[] getRecentArticles(int articleCount);

    ArticleOverviewDto[] getTodayArticles();

    ArticleOverviewDto[] getArticlesByCategory(String category);

    ArticleOverviewDto[] getRelatedArticles(String category);
}
