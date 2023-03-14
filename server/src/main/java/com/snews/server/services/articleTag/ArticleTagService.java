package com.snews.server.services.articleTag;


import com.snews.server.entities.ArticleTagEntity;
import com.snews.server.enumeration.ArticleTagEnum;

import java.util.List;

public interface ArticleTagService {
    void initTags();

    List<ArticleTagEntity> getAllTags();

    ArticleTagEntity getTag(ArticleTagEnum tagEnum);
}
