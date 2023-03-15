package com.snews.server.controllers;

import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.services.article.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final ArticleService articleService;

    public NewsController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/today")
    public ArticleOverviewDto[] getToday(){
        return this.articleService.getTodayArticles();
    }
}
