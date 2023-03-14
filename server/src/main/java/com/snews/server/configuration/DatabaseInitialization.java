package com.snews.server.configuration;

import com.snews.server.services.articleTag.ArticleTagService;
import com.snews.server.services.user.UserService;
import com.snews.server.services.userRole.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitialization implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;
    private final ArticleTagService articleTagService;

    public DatabaseInitialization(UserRoleService userRoleService, UserService userService, ArticleTagService articleTagService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.articleTagService = articleTagService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.initRoles();
        this.userService.initUsers();
        this.articleTagService.initTags();
    }


}
