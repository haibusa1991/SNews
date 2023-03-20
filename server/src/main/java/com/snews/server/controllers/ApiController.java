package com.snews.server.controllers;

import com.snews.server.dto.ArticleDto;
import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.services.article.ArticleService;
import com.snews.server.services.query.QueryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ArticleService articleService;
    private final QueryService queryService;

    public ApiController(ArticleService articleService, QueryService queryService) {
        this.articleService = articleService;
        this.queryService = queryService;
    }


    @GetMapping("/articleContent")
    public String getArticle() {

        boolean isAnonymous = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(e -> e.getAuthority().equals("ROLE_ANONYMOUS"));

        if (isAnonymous) {
            return "this is a short article";
        }
        return "this is a full article";
    }

    @GetMapping(value = "/csrf")
    public void getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        token.getToken();
    }


    @GetMapping("article/{href}")
    public ArticleDto getArticle(@PathVariable String href) {
        ArticleDto article = this.articleService.getArticle(href);

        boolean isAnonymous = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(e -> e.getAuthority().equals("ROLE_ANONYMOUS"));

        if (isAnonymous) {
            String[] content = article.getContent();
            article.setContent(new String[]{content[0]});
        }

        return article;
    }

    @GetMapping("article/home-articles")
    public ArticleOverviewDto[] getRecentArticles() {
        return this.articleService.getRecentArticles(12);
    }

    @GetMapping("search/{query}")
    //todo update all endpoints
    public ResponseEntity<ArticleOverviewDto[]> getSearchResults(@PathVariable String query) {
        ArticleOverviewDto[] result = this.queryService.articleSearch(query);
        if (result.length == 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(result);
    }

}
