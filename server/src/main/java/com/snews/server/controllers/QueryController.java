package com.snews.server.controllers;

import com.snews.server.dto.UserDto;
import com.snews.server.services.query.QueryService;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR')") //todo enable after finishing component
    public UserDto[] getAllUsers(@PathVariable String query) {
        return this.queryService.userSearch(query);
    }
}
