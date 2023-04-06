package com.snews.server.controllers;

import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.UserDto;
import com.snews.server.services.query.QueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/query")
public class QueryController {
private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping(path = "/user/{query}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<UserDto[]> getAllUsers(@PathVariable String query) {
        return new ResponseEntity<>(this.queryService.userSearch(query), HttpStatus.OK);
    }


    @GetMapping("/{query}")
    public ResponseEntity<ArticleOverviewDto[]> getSearchResults(@PathVariable String query) {
        ArticleOverviewDto[] result = this.queryService.articleSearch(query);

        if (result.length == 0) {
            //todo verify response - should it be bad request ot just an empty array.
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}
