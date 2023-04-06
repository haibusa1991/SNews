package com.snews.server.entities;

import com.snews.server.enumeration.ArticleCategoryEnum;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "article_categories")
public class ArticleCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ArticleCategoryEnum category;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "tags")
    private Set<ArticleEntity> articles;

    public ArticleCategoryEnum getCategory() {
        return category;
    }

    public ArticleCategoryEntity setCategory(ArticleCategoryEnum tag) {
        this.category = tag;
        return this;
    }

    public ArticleCategoryEntity() {
    }

    public ArticleCategoryEntity(ArticleCategoryEnum category) {
        this.category = category;
    }

    public Set<ArticleEntity> getArticles() {
        return articles;
    }

    public ArticleCategoryEntity setArticles(Set<ArticleEntity> articles) {
        this.articles = articles;
        return this;
    }
}
