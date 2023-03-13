package com.snews.server.repositories;

import com.snews.server.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    ArticleEntity getArticleEntityByHref(String href);
}
