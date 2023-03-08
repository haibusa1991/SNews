package com.snews.server.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

//todo remove together with testpost
class TestDto{
    private String parameterGoesHere;

    public String getParameterGoesHere() {
        return parameterGoesHere;
    }

    public TestDto setParameterGoesHere(String parameterGoesHere) {
        this.parameterGoesHere = parameterGoesHere;
        return this;
    }
}

@RestController
@RequestMapping("/api")
public class ApiController {

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
//        return token.getToken();
    }

    @PostMapping("/testpost")
    public String testPost(@RequestBody TestDto testDto ) {
        return "posting ok";
    }
}
