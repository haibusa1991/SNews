package com.snews.server.controllers;

import com.snews.server.dto.NewArticleDto;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.services.article.ArticleService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(path = "/new-article", consumes = MediaType.ALL_VALUE)
    public String publishArticle(NewArticleDto dto) throws InternalServerErrorException, IOException {
        return this.articleService.save(dto);
    }

}
