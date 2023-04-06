package com.snews.server.controllers;

import com.snews.server.dto.ArticleDto;
import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.NewArticleDto;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.services.article.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(path = "/new-article", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<String> newArticle(NewArticleDto dto) throws InternalServerErrorException, MalformedDataException {
        String href = this.articleService.addArticle(dto);
        return new ResponseEntity<>(href, HttpStatus.CREATED);
    }

    @GetMapping("/article-categories")
    public ResponseEntity<String[]> getArticleCategories() {
        return new ResponseEntity<>(articleService.getArticleCategories(), HttpStatus.OK);
    }


    @GetMapping("/{href}")
    public ArticleDto getArticle(@PathVariable String href) {
        return this.articleService.getArticle(href);
    }

    @GetMapping("/home-articles")
    public ArticleOverviewDto[] getHomepageArticles() {
        return this.articleService.getRecentArticles(12);
    }

    @GetMapping("/article-category/{category}")
    public ArticleOverviewDto[] getToday(@PathVariable String category) {

//        TODO remove logic from controller
        if (category.equalsIgnoreCase("today")) {
            return this.articleService.getTodayArticles();
        }

        return this.articleService.getArticlesByCategory(category);
    }
}
