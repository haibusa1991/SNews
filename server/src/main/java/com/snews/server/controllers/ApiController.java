package com.snews.server.controllers;

import com.snews.server.dto.ArticleDto;
import com.snews.server.services.article.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ArticleService articleService;

    public ApiController(ArticleService articleService) {
        this.articleService = articleService;
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

    @GetMapping(value="/csrf")
    public void getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
        token.getToken();
    }


    @GetMapping("article/{href}")
    public ArticleDto getArticle(@PathVariable String href) {

        return this.articleService.getArticle(href);
    }


}
