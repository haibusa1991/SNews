package com.snews.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/api/articleContent")
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
}
