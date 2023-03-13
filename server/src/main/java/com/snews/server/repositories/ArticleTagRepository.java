package com.snews.server.repositories;

import com.snews.server.entities.ArticleTagEntity;
import com.snews.server.enumeration.ArticleTagEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTagEntity, UUID> {

    ArticleTagEntity getArticleTagEntityByTag(ArticleTagEnum tag);
}
