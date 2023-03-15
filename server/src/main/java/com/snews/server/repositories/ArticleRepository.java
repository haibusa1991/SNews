package com.snews.server.repositories;

import com.snews.server.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    ArticleEntity getArticleEntityByHref(String href);

    @Query(nativeQuery = true, value =
            """
            SELECT *
            FROM articles
            ORDER BY published DESC
            LIMIT :articleCount
            """)
    List<ArticleEntity> getTopArticles(int articleCount);
}
