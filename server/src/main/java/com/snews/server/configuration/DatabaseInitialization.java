package com.snews.server.configuration;

import com.snews.server.services.articleCategory.ArticleCategoryService;
import com.snews.server.services.configuration.ConfigurationService;
import com.snews.server.services.user.UserService;
import com.snews.server.services.userRole.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitialization implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;
    private final ArticleCategoryService articleCategoryService;
    private final ConfigurationService configurationService;

    public DatabaseInitialization(UserRoleService userRoleService,
                                  UserService userService,
                                  ArticleCategoryService articleCategoryService,
                                  ConfigurationService configurationService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.articleCategoryService = articleCategoryService;
        this.configurationService = configurationService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.initRoles();
        this.userService.initUsers();
        this.articleCategoryService.initCategories();
        this.configurationService.initConfiguration();
    }


}
