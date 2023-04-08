package com.snews.server.services.articleCategory;


import com.snews.server.entities.ArticleCategoryEntity;
import com.snews.server.enumeration.ArticleCategoryEnum;

import java.util.List;

public interface ArticleCategoryService {
    void initCategories();

    List<ArticleCategoryEntity> getAllCategories();

    ArticleCategoryEntity getCategory(ArticleCategoryEnum tagEnum);
}
