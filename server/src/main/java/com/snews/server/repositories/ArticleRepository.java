package com.snews.server.repositories;

import com.snews.server.entities.ArticleEntity;
import com.snews.server.entities.ArticleTagEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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


    List<ArticleEntity> findAllByPublishedAfterOrderByPublishedDesc(LocalDateTime date);

    List<ArticleEntity> findAllByTagsContainingIgnoreCaseOrderByPublishedDesc(ArticleTagEntity tag);

    List<ArticleEntity> findAllByOrderByPublishedDesc(Pageable pageable);

    List<ArticleEntity> findAllByPublishedBetweenOrderByPublishedDesc(LocalDateTime start, LocalDateTime end);

    List<ArticleEntity> findAllByHeadingContainingIgnoreCase(String keyword);

    @Query(value = """
            SELECT ae.heading
            FROM ArticleEntity ae
            WHERE ae.heading LIKE %:keyword%
            """)
    List<String> getAllHeadingsByKeywordMatch(String keyword);

    ArticleEntity getArticleEntityByHeading(String heading);

    @Query(value = """
            SELECT *
            FROM articles
            WHERE heading REGEXP :keywords
            LIMIT 50
            """,nativeQuery = true)
    List<ArticleEntity> getArticlesByKeywords(String keywords);
}

