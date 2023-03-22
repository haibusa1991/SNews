package com.snews.server.services.query;

import com.snews.server.dto.ArticleOverviewDto;

public interface QueryService {
    ArticleOverviewDto[] articleSearch(String query);
}
