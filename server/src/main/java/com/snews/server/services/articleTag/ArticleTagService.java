package com.snews.server.services.articleTag;


import com.snews.server.entities.ArticleCategoryEntity;
import com.snews.server.enumeration.ArticleCategoryEnum;

import java.util.List;

public interface ArticleTagService {
    void initTags();

    List<ArticleCategoryEntity> getAllTags();

    ArticleCategoryEntity getCategory(ArticleCategoryEnum tagEnum);
}
