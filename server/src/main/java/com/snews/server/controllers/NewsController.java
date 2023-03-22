package com.snews.server.controllers;

import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.services.article.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/news")
public class NewsController {
    private final ArticleService articleService;

    public NewsController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{category}")
    public ArticleOverviewDto[] getToday(@PathVariable String category) {
        if (category.equalsIgnoreCase("today")) {
            return this.articleService.getTodayArticles();
        }

        return this.articleService.getArticlesByCategory(category);
//        return new ArticleOverviewDto[0];
    }
}
