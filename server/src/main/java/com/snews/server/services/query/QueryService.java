package com.snews.server.services.query;

import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.UserDto;

public interface QueryService {
    ArticleOverviewDto[] articleSearch(String query);

    UserDto[] userSearch(String username);
}
