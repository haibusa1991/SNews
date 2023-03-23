package com.snews.server.controllers;

import com.snews.server.dto.NewArticleDto;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.services.article.ArticleService;
import com.snews.server.services.file.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "/article")
public class ArticleController {

    private final ArticleService articleService;
    private final FileService fileService;

    public ArticleController(ArticleService articleService, FileService fileService) {
        this.articleService = articleService;
        this.fileService = fileService;
    }

    @PostMapping(path = "/new-article", consumes = MediaType.ALL_VALUE)
    public String publishArticle(NewArticleDto dto) throws InternalServerErrorException, IOException, MalformedDataException {
        return this.articleService.save(dto);
    }

    @GetMapping("/article-categories")
    public String[] getArticle() {
        return articleService.getArticleCategories();
    }


}
