package com.snews.server.repositories;

import com.snews.server.entities.ArticleCategoryEntity;
import com.snews.server.enumeration.ArticleCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleCategoryEntity, UUID> {

    ArticleCategoryEntity getArticleTagEntityByTag(ArticleCategoryEnum tag);
}
